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

### Similar Historical Incidents

**AWS S3 Outage (2017):** A typo in a command took down a large portion of S3, affecting many websites. Like Cloudflare's incident, it showed how a small configuration error can have massive cascading effects.

**Facebook Global Outage (2021):** A BGP configuration error took down Facebook, Instagram, and WhatsApp globally. Similar to this incident, it demonstrated how infrastructure changes can cause widespread service disruption.

**Google Cloud Outage (2019):** Network congestion in one region cascaded globally due to automated systems. This shows how automated systems, like Cloudflare's 5-minute configuration updates, can amplify problems.

