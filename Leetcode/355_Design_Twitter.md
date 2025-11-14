### 355. Design Twitter
### Problem Link: [Design Twitter](https://leetcode.com/problems/design-twitter/)

### Intuition/Main Idea
This problem asks us to design a simplified version of Twitter where users can post tweets, follow/unfollow other users, and view the 10 most recent tweets in their news feed (including their own tweets and tweets from users they follow).

The key insights for this design are:
1. We need to store tweets with timestamps to maintain chronological order
2. We need to track follower relationships between users
3. For the news feed, we need to efficiently merge tweets from multiple users

The most elegant approach is to use:
- A HashMap to store user-follower relationships
- A HashMap to store user-tweet relationships
- A global timestamp to order tweets
- A PriorityQueue (max heap) to efficiently merge and retrieve the most recent tweets

This design allows us to:
- Post tweets in O(1) time
- Follow/unfollow users in O(1) time
- Generate news feeds in O(n log k) time, where n is the number of users followed and k is the number of tweets to retrieve

### Code Mapping

| Problem Requirement | Java Code Section (Relevant Lines) |
|---------------------|-----------------------------------|
| Post a tweet | `public void postTweet(int userId, int tweetId)` |
| Follow a user | `public void follow(int followerId, int followeeId)` |
| Unfollow a user | `public void unfollow(int followerId, int followeeId)` |
| Get news feed | `public List<Integer> getNewsFeed(int userId)` |
| Store user-follower relationships | `Map<Integer, Set<Integer>> followMap = new HashMap<>();` |
| Store user-tweet relationships | `Map<Integer, List<Tweet>> tweetMap = new HashMap<>();` |

### Final Java Code & Learning Pattern

```java
// [Pattern: Design with HashMap and PriorityQueue]
class Twitter {
    // Class to store tweet info
    private class Tweet {
        int tweetId;
        int timestamp;
        
        public Tweet(int tweetId, int timestamp) {
            this.tweetId = tweetId;
            this.timestamp = timestamp;
        }
    }
    
    // Maps user to their tweets
    private Map<Integer, List<Tweet>> tweetMap;
    
    // Maps user to their followees
    private Map<Integer, Set<Integer>> followMap;
    
    // Global timestamp for ordering tweets
    private int timestamp;
    
    public Twitter() {
        tweetMap = new HashMap<>();
        followMap = new HashMap<>();
        timestamp = 0;
    }
    
    public void postTweet(int userId, int tweetId) {
        // Create user if doesn't exist
        if (!tweetMap.containsKey(userId)) {
            tweetMap.put(userId, new ArrayList<>());
        }
        
        // Add tweet with current timestamp
        tweetMap.get(userId).add(new Tweet(tweetId, timestamp++));
    }
    
    public List<Integer> getNewsFeed(int userId) {
        // Max heap to sort tweets by timestamp (most recent first)
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.timestamp - a.timestamp);
        
        // Add user's own tweets
        if (tweetMap.containsKey(userId)) {
            for (Tweet tweet : tweetMap.get(userId)) {
                maxHeap.offer(tweet);
            }
        }
        
        // Add tweets from followees
        if (followMap.containsKey(userId)) {
            for (int followeeId : followMap.get(userId)) {
                if (tweetMap.containsKey(followeeId)) {
                    for (Tweet tweet : tweetMap.get(followeeId)) {
                        maxHeap.offer(tweet);
                    }
                }
            }
        }
        
        // Get the 10 most recent tweets
        List<Integer> newsFeed = new ArrayList<>();
        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            newsFeed.add(maxHeap.poll().tweetId);
            count++;
        }
        
        return newsFeed;
    }
    
    public void follow(int followerId, int followeeId) {
        // Don't follow yourself
        if (followerId == followeeId) {
            return;
        }

        followMap.computeIfAbsent(followerId, k -> new HashSet<>()).add(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        // Check if follower exists and is following followee
        if (followMap.containsKey(followerId)) {
            followMap.get(followerId).remove(followeeId);
        }
    }
}

/**
 * Your Twitter object will be instantiated and called as such:
 * Twitter obj = new Twitter();
 * obj.postTweet(userId,tweetId);
 * List<Integer> param_2 = obj.getNewsFeed(userId);
 * obj.follow(followerId,followeeId);
 * obj.unfollow(followerId,followeeId);
 */
```

### Alternative Implementation (Using a More Efficient News Feed Generation)

```java
// [Pattern: Design with HashMap and K-way Merge]
class Twitter {
    private static int timestamp = 0;
    
    // Tweet class with next pointer for linked list
    private class Tweet {
        int id;
        int time;
        Tweet next;
        
        public Tweet(int id) {
            this.id = id;
            this.time = timestamp++;
            this.next = null;
        }
    }
    
    // User class to store user data
    private class User {
        int id;
        Set<Integer> followed;
        Tweet tweetHead;
        
        public User(int id) {
            this.id = id;
            this.followed = new HashSet<>();
            this.followed.add(id);  // Follow themselves
            this.tweetHead = null;
        }
        
        public void follow(int id) {
            followed.add(id);
        }
        
        public void unfollow(int id) {
            if (id != this.id) {  // Can't unfollow themselves
                followed.remove(id);
            }
        }
        
        public void post(int tweetId) {
            Tweet tweet = new Tweet(tweetId);
            tweet.next = tweetHead;
            tweetHead = tweet;
        }
    }
    
    private Map<Integer, User> userMap;
    
    public Twitter() {
        userMap = new HashMap<>();
    }
    
    public void postTweet(int userId, int tweetId) {
        if (!userMap.containsKey(userId)) {
            userMap.put(userId, new User(userId));
        }
        userMap.get(userId).post(tweetId);
    }
    
    public List<Integer> getNewsFeed(int userId) {
        List<Integer> result = new ArrayList<>();
        if (!userMap.containsKey(userId)) {
            return result;
        }
        
        // Use a max heap to sort tweets by time
        PriorityQueue<Tweet> maxHeap = new PriorityQueue<>((a, b) -> b.time - a.time);
        
        // Get all followees' tweet heads
        Set<Integer> followed = userMap.get(userId).followed;
        for (int followeeId : followed) {
            if (userMap.containsKey(followeeId) && userMap.get(followeeId).tweetHead != null) {
                maxHeap.offer(userMap.get(followeeId).tweetHead);
            }
        }
        
        // Get the 10 most recent tweets
        int count = 0;
        while (!maxHeap.isEmpty() && count < 10) {
            Tweet tweet = maxHeap.poll();
            result.add(tweet.id);
            count++;
            
            // Add the next tweet from the same user
            if (tweet.next != null) {
                maxHeap.offer(tweet.next);
            }
        }
        
        return result;
    }
    
    public void follow(int followerId, int followeeId) {
        if (!userMap.containsKey(followerId)) {
            userMap.put(followerId, new User(followerId));
        }
        if (!userMap.containsKey(followeeId)) {
            userMap.put(followeeId, new User(followeeId));
        }
        userMap.get(followerId).follow(followeeId);
    }
    
    public void unfollow(int followerId, int followeeId) {
        if (userMap.containsKey(followerId)) {
            userMap.get(followerId).unfollow(followeeId);
        }
    }
}
```

### Complexity Analysis
- **Time Complexity**:
  - `postTweet`: O(1)
  - `follow`: O(1)
  - `unfollow`: O(1)
  - `getNewsFeed`: O(n log k) where n is the total number of tweets from the user and their followees, and k is the number of tweets in the heap (at most 10 * number of followees)

- **Space Complexity**: O(U + T) where U is the number of users and T is the total number of tweets.

### Similar Problems
1. **LeetCode 146: LRU Cache** - Design a data structure with efficient get and put operations.
2. **LeetCode 380: Insert Delete GetRandom O(1)** - Design a data structure with constant time operations.
3. **LeetCode 381: Insert Delete GetRandom O(1) - Duplicates allowed** - Similar to 380 but with duplicates.
4. **LeetCode 295: Find Median from Data Stream** - Design a data structure that supports finding the median.
5. **LeetCode 460: LFU Cache** - Design a data structure with least frequently used cache eviction policy.
6. **LeetCode 432: All O`one Data Structure** - Design a data structure to store counts of strings.
7. **LeetCode 1396: Design Underground System** - Design a system to calculate average travel times.
8. **LeetCode 1472: Design Browser History** - Design a browser history management system.
