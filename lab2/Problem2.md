---
title: Problem 2

---

# Problem 2

Dan Jeong, Youhui He, Malia Porter


## Case 1

- Time Complexity?: $\Theta (n^2) * q$ where $q$ is the size of the element sets. Since we need to calculate ~n^2 similarities and each similarity takes ~q time to calculate O((n^2) * q). The algorithm is also $\Theta (n^2) * q$ because we do not terminate early.

- Space Complexity?: N^2

```python
# Space: N^2 - We have n keys
similarities = {
    0: {
        1:  n
        2:
        ...
    }
    1: {
        2:
    }
    ...
}

for idx in range(0, len(input)-2): #Time: n
    for idx2 in range(idx+1, len(input)-1): # Time: n
        
        a = input[idx] # set 1
        b = input[idx2] # set 2
        
        # The time complexity of finding the intersection 
        # is O(min(len(a), len(b))) because you can 
        # find the intersection by using the shorter set's elements
        # as search keys & search time is constant. 
        intersection = a & b 
        # Time: O(min(len(a), len(b)))
        # Space: O(min(len(a), len(b))) 
        

        size_inter = len(intersection) 
        size_a = len(a)
        size_b = len(b)

        size_union = size_a + size_b - size_inter

        sim = size_inter / size_union
        
        similarities[idx][idx2] = sim
        
            
return similarities
            
```
    
## Case 2

- Time Complexity: O(n^2)*
- Space Complexity: 
```python
# Space: N^2 - We have n keys
similarities = {}

appearance = {
    a: [1, 2]
}

intersections = {}

for idx, s in enumerate(input): # Time: n * q
    for element in s:
        appearance[element].append(idx)

# We are not considering pairs of sets anymore, 
# the hashset is linear.

for key, set_indicies in appearance.items(): # Time: q 
    for idx in range(0, len(set_indicies)-2): # t
        for idx2 in range(idx, len(set_indicies)-1):
            intersections[idx][idx2] += 1

for idx1, row in enumerate(intersections):
    for idx2, col in enumerate(row): 
        similarities[idx1][dix2] = intersections[idx1][idx2] / (len(input[idx1]) + len(input[idx2]) - intersections[idx1][idx2])

```
