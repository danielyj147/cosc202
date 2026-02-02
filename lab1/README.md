# Problem 1

> Youhui He, Daniel Jeong, Malia Porter

input array: A

* Overall Time Complexity: $\Theta(n)$
* Overall Space Complexity: $\Theta(1)$

```
a = -1
b = n

# Step 1: Find the maximum index of the lower bound.
# Time Complexity: O(n)
For each item in A - 1 
    if item is greater than item+1 then set `a` as item index
        break
        
# Step 1.5: If a is -1, the array is sorted. 
if a == -1:
    return (0,0)

# Step 2: Find the minimum index of the upper bound.
# Time Complexity: O(n)
For item in range(len(n), 0)
    if item is smaller than item-1 then set `b` as item index
        break
    
# Becasue the item on index a/b may not be the local min/maximum value in the range we need to find the actual min/maximum value. 
min_num = min(A[a:b]) # Time Complexity: O(n)
max_num = max(A[a:b]) # Time Complexity: O(n)

# Step 3:Because we vefiried that `a` is the maximum of the lower bound, we consider the indices smaller than `a`
# Time Complexity: O(n)
For each item before `a`
    if item is greater than `min_num`
        set `a` as the item index
        break

# Step 4: Because we vefiried that `b` is the minimum of the upper bound, we consider the indices greater than `a`
# Time Complexity: O(n)
For each item after `b` (backward)
    if item is smaller than `b`
        set `b` as the item index
        break

return `a` and `b`
```
