# Lab 3
Dan Jeong, Youhui He, Malia Porter

- Time complexity: $O(n + y \log y)$
- Space complexity: $\Theta(y)$

## 

```python=

years  = {} # space: n
max_pop = 0
max_year = 0

for person in data: #n 
    birth = person.birth_year
    death = person.death_year
    
    years[birth] += 1
    years[death+1] -= 1

# sorting keys to properly in/decrement diff in order.
sorted_years = years.keys().sort() # y log y

for year in sorted_years: # y
    diff = years[year]
    current += diff
    if current > max_pop:
        max_pop = current
        max_year = year

return PopOoutput(max_year, max_count)

```