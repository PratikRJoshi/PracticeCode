Source: https://blog.cloudflare.com/18-november-2025-outage/

### Rules for LLM
- Use the latest information from the source
- Explain the issue in a way that is easy to understand in only a few sentences
- Eli5 the responses as well
- Help me learn about the architecture and the concepts used in this setup
- Suggest any similar incidents in the past from other companies and how they were resolved

## Q&A Section

### 1. What is Turnstile?

**Simple Answer:** Turnstile is Cloudflare's modern replacement for those annoying "click all the traffic lights" CAPTCHAs.

**ELI5:** You know how websites sometimes make you prove you're human by clicking on pictures? Turnstile does that check automatically in the background without bothering you. It's like having a bouncer at a club who can tell you're not a robot just by watching how you walk up to the door.

**Technical Details:** Turnstile runs JavaScript checks in your browser to analyze your behavior patterns and determine if you're human or a bot. It's privacy-focused and provides a seamless user experience compared to traditional CAPTCHAs.

### 2. What are WAF rules?

**Simple Answer:** WAF (Web Application Firewall) rules are like security guards that check every visitor (web request) before letting them into a website.

**ELI5:** Imagine your website is a building, and WAF rules are the security guards at the entrance. They have a list of "bad guys" (malicious requests) and "suspicious behavior" (attack patterns). When someone tries to enter, the guards check them against their list and decide whether to let them in, ask them questions, or kick them out.

**Technical Details:** WAF rules filter HTTP requests based on patterns that indicate attacks like SQL injection, cross-site scripting (XSS), or DDoS attempts. They can block, challenge, log, or allow requests based on predefined criteria.

### 3. The ClickHouse "Good Data vs Bad Data" Problem

**Simple Answer:** Imagine a recipe book being updated page by page, but the chef keeps grabbing random pages to cook from - sometimes getting the old recipe, sometimes the new one.

**ELI5:** Think of it like this: Cloudflare has a big cookbook (ClickHouse database) with recipes for how their servers should work. They were updating this cookbook to make it better, but they were doing it slowly - updating one page every few minutes. 

The problem was that every 5 minutes, a chef (the system) would grab a random page to cook from. If they grabbed an updated page, they'd make "bad food" (bad configuration) because the new recipe wasn't quite right yet. If they grabbed an old page, they'd make "good food" (good configuration). So every 5 minutes, there was a coin flip between good and bad configurations being sent to all Cloudflare servers worldwide.

**Technical Details:** The ClickHouse cluster was being gradually updated for permissions management. Since queries could hit either updated or non-updated nodes, the configuration files generated every 5 minutes would be inconsistent, leading to intermittent service issues.

### 4. What is ClickHouse and its role?

**Simple Answer:** ClickHouse is a super-fast database designed for analyzing huge amounts of data quickly.

**ELI5:** Think of ClickHouse like a giant, super-organized library where you can ask questions like "How many people visited our website from California last Tuesday?" and get answers in seconds, even if you have billions of visitors. In this incident, Cloudflare was using it to store information about how their servers should be configured.

**Technical Details:** ClickHouse is an open-source columnar database optimized for OLAP (Online Analytical Processing). Cloudflare uses it to generate configuration files by querying large datasets. The gradual cluster updates caused inconsistent query results.

### 5. Workers KV and Access dependency on core proxy

**Simple Answer:** Workers KV and Access are like apps on your phone that need the internet to work - if your WiFi (core proxy) goes down, these apps stop working too.

**ELI5:** Imagine Cloudflare's network like a big city. The "core proxy" is like the main highway system that everything depends on. "Workers KV" is like a giant storage warehouse, and "Access" is like a security checkpoint system. Both the warehouse and security checkpoint need the highway to function. When the highway has problems, trucks can't get to the warehouse and people can't reach the security checkpoints.

**Technical Details:** Workers KV (key-value storage) and Access (Zero Trust security) are Cloudflare services that depend on the core proxy infrastructure for routing and processing requests. Any core proxy issues cascade to these dependent services.

### 6. Rust Panic vs Java Exception

**Rust Code that panics:**
```rust
fn main() {
    let numbers = vec![1, 2, 3];
    let value = numbers[10]; // This will panic! Index 10 doesn't exist
    println!("Value: {}", value);
}
```

**Equivalent Java code:**
```java
public class Main {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3};
        int value = numbers[10]; // This throws ArrayIndexOutOfBoundsException
        System.out.println("Value: " + value);
    }
}
```

**ELI5:** Both languages are trying to grab the 11th item from a box that only has 3 items. Rust says "PANIC! I can't do this!" and immediately stops everything. Java says "Exception! Something went wrong!" and also stops (unless you catch it). It's like asking for the 11th floor button in a 3-story building elevator.

### 7. Core dumps overwhelming system resources

**Simple Answer:** When the system crashed, it tried to save a "snapshot" of what went wrong, but these snapshots were so big they filled up all the storage space.

**ELI5:** Imagine your computer crashes and tries to save everything that was in its "brain" (memory) to help figure out what went wrong later. But your computer's brain is HUGE, so these "crash reports" are like trying to photocopy an entire library every time something goes wrong. Pretty soon, you run out of space to store all these photocopies, making the problem even worse.

**Technical Details:** Core dumps are memory snapshots created when programs crash. During the outage, the system generated excessive core dumps that consumed disk space and I/O resources, exacerbating the incident by overwhelming system resources.

### 8. Global kill switches for features

**Simple Answer:** These are emergency "OFF" buttons that can instantly turn off specific features across all of Cloudflare's servers worldwide.

**ELI5:** Imagine you're running a chain of restaurants and one of your new menu items is making people sick. Instead of calling each restaurant individually to stop serving that item, you want a big red button at headquarters that instantly tells ALL restaurants "STOP SERVING THE CHICKEN SANDWICH" at the same time.

**How it helps:** If a feature starts causing problems, operators can immediately disable it globally without waiting for code deployments or individual server updates.

**Where they'd be placed:** These switches would be in Cloudflare's central control systems, likely integrated into their configuration management, feature flag systems, and load balancers, allowing instant global feature toggles.

### 9. How did the SQL query lead to the whole outage? (The Complete Chain of Events)

**Simple Answer:** A database permission change accidentally made a SQL query return twice as much data as expected, which broke a system that had a hard limit on file size.

**ELI5 - The Domino Effect:**
Imagine you have a vending machine that's programmed to only accept exactly 60 coins. Every 5 minutes, someone counts coins from a jar and puts them in the machine. 

1. **The Setup Change:** Someone "organized" the coin jar by adding a second compartment, but forgot to tell the coin-counter about it.

2. **The Counting Problem:** Now when the coin-counter goes to count, they accidentally count from BOTH compartments instead of just one, getting 120 coins instead of 60.

3. **The Machine Breaks:** The vending machine gets 120 coins, but it's only designed for 60. It says "ERROR! TOO MANY COINS!" and shuts down completely.

4. **The Cascade:** This vending machine controls the power to an entire shopping mall. When it shuts down, the whole mall goes dark.

5. **The Intermittent Problem:** Every 5 minutes, someone tries again. Sometimes they accidentally count from both compartments (120 coins = mall goes dark), sometimes they count from just one (60 coins = mall works fine). So the mall keeps flickering on and off every 5 minutes.

**The Technical Chain of Events:**

```
Step 1: Database Permission Update
├── Cloudflare updated ClickHouse database permissions
├── Goal: Better security and access control
└── Unintended side effect: Changed query behavior

Step 2: SQL Query Behavior Change
├── Query was supposed to get data from 'default' database only
├── After permission change: Query now gets data from BOTH 'default' AND 'r0' databases
├── Result: Duplicate entries (60 features → 400+ features)
└── File size doubled unexpectedly

Step 3: Hard-Coded Limit Exceeded
├── Bot Management system had limit: 200 features max
├── Normal usage: ~60 features
├── After query change: 400+ features
└── System response: PANIC and crash (like Rust panic we discussed)

Step 4: Configuration File Generation (Every 5 minutes)
├── Automated system queries ClickHouse cluster
├── Cluster being updated gradually (some nodes updated, some not)
├── Query hits updated node → Bad config (400+ features) → System crash
├── Query hits old node → Good config (60 features) → System works
└── Result: Intermittent failures every 5 minutes

Step 5: Global Propagation
├── Bad config file spreads to all Cloudflare servers worldwide
├── Core proxy systems crash when they get the oversized file
├── Services depending on core proxy (Workers KV, Access) also fail
└── Result: Global outage with HTTP 5xx errors
```

**Why This Is So Dangerous:**
1. **Silent Failure:** The permission change didn't throw an error - it just quietly changed behavior
2. **Hard-Coded Limits:** The 200-feature limit was buried in code, not easily adjustable
3. **Automated Propagation:** The bad config spread automatically to all servers
4. **Dependency Cascade:** Core proxy failure brought down everything that depended on it
5. **Intermittent Nature:** Made it hard to diagnose because it worked sometimes

**The ACTUAL SQL Query That Caused the Outage:**
```sql
-- The real query that broke everything:
SELECT name, type 
FROM system.columns 
WHERE table = 'http_requests_features' 
ORDER BY name;
```

**What This Query Does:**
- `system.columns` is a special ClickHouse table that contains metadata about all columns in all databases
- It's like asking "Show me all the column names and types for the table called 'http_requests_features'"
- This metadata was used to generate the Bot Management feature configuration file

**The Permission Problem Explained:**

**Before the permission change:**
```sql
-- Query only saw columns from the 'default' database
-- Result: ~60 features (columns)
SELECT name, type FROM system.columns 
WHERE table = 'http_requests_features'
-- Only returned: default.http_requests_features columns
```

**After the permission change:**
```sql
-- Same query, but now sees columns from BOTH databases
-- Result: ~400+ features (duplicate columns from both databases)
SELECT name, type FROM system.columns 
WHERE table = 'http_requests_features'
-- Now returned: 
-- default.http_requests_features columns +
-- r0.http_requests_features columns (duplicates!)
```

**Why This Happened:**
1. **Missing Database Filter:** The query didn't specify which database to look in (`WHERE database = 'default'`)
2. **Permission Expansion:** The update gave access to the `r0` database (used for distributed queries)
3. **Duplicate Tables:** Both `default` and `r0` databases had the same table name with the same columns
4. **Metadata Explosion:** The query returned the same column information twice, doubling the feature file size

### The Complete Technical Breakdown

**What We Know About the Original Query and Permission Change:**

**1. The Original SQL Query (Now Confirmed):**
```sql
SELECT name, type FROM system.columns WHERE table = 'http_requests_features' ORDER BY name;
```

**What This Query Actually Does:**
- `system.columns` is a special ClickHouse system table containing metadata about ALL columns in ALL databases
- It's asking: "Show me the column names and data types for any table named 'http_requests_features'"
- This metadata was used to generate the Bot Management feature configuration file
- **Critical Flaw:** No database filter specified!

**2. The Permission Change Explained:**

**Before the Permission Change:**
- The Bot Management system only had access to the `default` database
- When the query ran, `system.columns` only showed columns from `default.http_requests_features`
- Result: ~60 feature columns returned
- File size: Normal, well within the 200-feature limit

**After the Permission Change:**
- Cloudflare updated permissions to make access to underlying tables "explicit"
- This accidentally granted access to the `r0` database (used for distributed queries)
- The `r0` database is like a "shadow" database containing the same tables as `default`
- Now `system.columns` showed columns from BOTH databases:
  - `default.http_requests_features` columns
  - `r0.http_requests_features` columns (duplicates!)
- Result: ~400+ feature columns (duplicates counted twice)
- File size: Exceeded the hard-coded 200-feature limit

**3. Why This Is So Dangerous - The "Library Analogy":**

Imagine you're a librarian's assistant, and every day you ask: "Show me all books titled 'Programming 101'" to create a reading list.

- **Before:** You only had access to Building A, so you got 1 book
- **After:** Someone gave you access to Building B (which has the same books), so now you get 2 identical books
- **Problem:** Your reading list system was designed for 1 book max, so it crashes when it gets 2

**4. The Technical Root Cause:**

**Vulnerable Query Pattern:**
```sql
-- VULNERABLE: No database specified
SELECT name, type FROM system.columns WHERE table = 'http_requests_features'
```

**Safe Query Pattern:**
```sql
-- SAFE: Database explicitly specified
SELECT name, type FROM system.columns 
WHERE table = 'http_requests_features' 
  AND database = 'default'
```

**5. What Makes This Incident Particularly Insidious:**

1. **Silent Failure:** No error messages - the query just returned more data
2. **Implicit Assumption:** Code assumed only one database would ever be accessible
3. **Metadata Query:** This wasn't even querying business data - just table structure
4. **Permission Side Effect:** The permission change was for security, not functionality
5. **Hard-Coded Limits:** The 200-feature limit was buried in code, not configurable
6. **Automated Propagation:** Bad config spread to all servers within minutes

**6. The Cascade Effect:**
```
Permission Change → Query Returns Duplicates → File Too Large → 
System Panic → Core Proxy Crash → Global Outage
```

**7. Lessons for Software Engineers:**

**Defensive Programming:**
- Always specify database/schema in queries, even if only one exists today
- Validate input sizes, even from "trusted" internal systems
- Make limits configurable, not hard-coded
- Log when data patterns change unexpectedly

**Testing:**
- Test permission changes in isolated environments
- Simulate what happens when queries return more data than expected
- Test with duplicate data scenarios

**Architecture:**
- Avoid single points of failure
- Implement circuit breakers for oversized responses
- Add monitoring for data size anomalies

This incident perfectly demonstrates how modern distributed systems can fail in unexpected ways - not from any single component breaking, but from **interactions** between components when underlying assumptions are violated.

### 10. The Complete Story

#### The Original SQL Query

The actual query that caused the outage was surprisingly simple:

```sql
SELECT name, type 
FROM system.columns 
WHERE table = 'http_requests_features' 
ORDER BY name;
```

**Key insight:** This wasn't querying business data - it was querying metadata about the database structure itself.

---

#### The Permission Change

Cloudflare made a seemingly innocent change to ClickHouse permissions:

- Updated permissions to make access to underlying tables "explicit"
- Gave the Bot Management system access to the **r0 database** (in addition to the default database)
- The r0 database contains underlying tables used for distributed queries (think of it as a "shadow" database)

---

#### How the Permission Change Broke Everything

**The Librarian Analogy:**

> Imagine asking a librarian: "Show me all books titled 'Programming 101'"
> 
> You expect one book. But the librarian just got access to a second library building that also has a copy of "Programming 101". 
> 
> Now when you ask the same question, you get TWO books with the same title - one from each building.

**What Happened Technically:**

- **Before:** Query only had permission to see `system.columns` entries from the default database
- **After:** Query could see `system.columns` entries from both default AND r0 databases
- **Problem:** Both databases had the same table (`http_requests_features`) with the same columns
- **Result:** The query returned duplicate metadata - every column appeared twice

---

#### The Critical Flaw

The query was missing a crucial filter that would have prevented this:

```sql
-- ❌ Original vulnerable query
SELECT name, type 
FROM system.columns 
WHERE table = 'http_requests_features' 
ORDER BY name;

-- ✅ Defensive query that would have prevented the incident
SELECT name, type 
FROM system.columns 
WHERE table = 'http_requests_features' 
  AND database = 'default'  -- This one line would have saved everything!
ORDER BY name;
```

---

#### The Lesson

This incident is a perfect example of **defensive programming**:

- The original query worked fine when there was only one database
- It broke when the environment changed
- Classic case of an **implicit assumption** (only one database accessible) being violated by a system change

The beauty (and terror) of this incident is how simple the root cause was - a missing `WHERE` clause in a metadata query!

### Similar Historical Incidents

**[AWS S3 Outage (2017)](https://aws.amazon.com/message/41926/):** A typo in a command took down a large portion of S3, affecting many websites. Like Cloudflare's incident, it showed how a small configuration error can have massive cascading effects.

**[Facebook Global Outage (2021)](https://engineering.fb.com/2021/10/05/networking-traffic/outage-details/):** A BGP configuration error took down Facebook, Instagram, and WhatsApp globally. Similar to this incident, it demonstrated how infrastructure changes can cause widespread service disruption.

**[Google Cloud Outage (2019)](https://cloud.google.com/blog/topics/inside-google-cloud/an-update-on-sundays-service-disruption):** Network congestion in one region cascaded globally due to automated systems. This shows how automated systems, like Cloudflare's 5-minute configuration updates, can amplify problems.

