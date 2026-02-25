---
title: Problem 6

---

# Problem 6 - Spelling Suggestions

Malia, Dan, Youhui

Distance

1. insertion
2. deletion
3. substitution
4. exact match


```python
# ca? dist = 1
trie = {}

def suggestions(target: str, dist: int):
    # given target find words within dist
    
    ret = help(target, dist, trie[root]) # words within the distance
    
def help(target: str, dist:int, node:Node, results: HashMap<>, string:str) -> None:    
    if len(target) == 0 && node.isWord:
        results.add(string)
    
    if dist < 0:
        return null
    
    next = target[0]
    for child in node:
        if (child == next):
            help(target[1:], dist, child, word+next) # exact match
        else:
            help(target, dist-1, child) # insert
            help(target[1:], dist-1, child, ) #substitution
            help(target[1:], dist-1, Node) #delection
    
    
# suggestions
# add
```