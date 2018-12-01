# Halp
## Iteration 3 - Review & Retrospect

Review meeting will occur on November 30, in person in Myhal's Atrium.

#### Decisions that turned out well

 Branching went well. We managed our branches efficiently, having four major branches; Master, Backend, HTTPStuff and Mock. Better   organization led to better merging and more organized work. We ran into almost no merge conflicts, and this was bolstered by our decision to do most of the front end via pair programming.
 
 For the largest portions of the application, other than the backend service, we used pair programming sessions. The active members of the group would meet in MyHal and all work on the code together for many hours at a time. This was very effective because each member had different insights, and with each of us having a laptop would could all take turns looking at each others code and problems. We were able to learn a lot from each other and tackle every major problem as a unit. 
 
 Active members weere in constant communication. By tackling any problem an individual member faced together, we were able to all learn from each other and solve issues very quickly. This is similar to the benefits of pair programming, but we also did this when we were not all together, over discord. The active members of the group met up in person or on discord mutliple times throughout the sprint. 
 

#### Decisions that did not turn out as well as we hoped
 
 Communication with non-active memebrs was fairly poor. The more active members of the group could have done more to motivate or contact the non-active members. We are usure if putting more effort into motivating the less active members would have resulted in larger output from them. However, by not pushing those members to do more, a lot of untapped resources were lost and overall the amount of output the team could have produced was significantly reduced. 
 
 Work organization, division of work and order of task completion. We didn't properly set expectations and we didn't complete tasks in order of priority, resulting in us having non-important tasks completed while some of our core/important features could not be implemented due to the time constraints.
 
 Because we were unable to estimate how much work we could get done, we decided to additionally work on a less complicated version of the app at the same time. This ended up splitting our resources, and the version we thought was less complicated(using mock data) ended up taking more of our time than we initially thought. This resulted in neither implementation being fully complete, with large portions of each being functional. If we had instead dedicated all of our time to one implementation, we may have been able to complete more work and would have wasted less time and effort. 


#### Planned changes
 
 The most important thing would be to to make sure communication with all members is strong. The active members are communicating effectively, but more could be done to include everyone in the conversation. Motivating all members is something that needs to be focused on in the future. 
 
 Another thing is not wasting resources on features we do not plan to keep. While these features would be useful for testing, the small group size and limited time window for the project forces the group to be as efficient as possible. We didn't really have time to dedicate extra resources to temporary or non-integral features. 
 
 Following from the previous point, getting a clearer vision of what features were integral to the project and focusing on those would have resulted in more throughput. Overall, the scope needed to be reduced since many of the members had outside work and final projects that limited their ability to put in as much time as we initially expected. 


## Product - Review

#### Goals and/or tasks that were met/completed:
 
The back end service is completed, well tested and deployable on AWS:
   - Handling Requests
   - Database and Serialization
   - [API documentation](Artifacts/API.md)
   - [Test coverage](Artifacts/coverage.png)

 
 Google Maps API is completed.
 
 Large portions of our HTTP interface in our mobile app is completed:
    -Creating users
    -Authentication for login
    -creating requests
    -Getting a list of requests for a user submitted by that user
 
 Json serialization and Deserialization:
       -Builder pattern for all objects in the application
       -Gson api integration for serializing and deserializing the objects


 

#### Goals and/or tasks that were planned but not met/completed:
There are multiple tasks that weren't completed, but all of them fall under the same class. Our HTTP interface is not completed, and these are the following tasks in that interface that were not implemented fully: 
   - workers can't accept jobs: we wanted workers to be able to accept jobs, and send this to the backend server. We were unable to implement this because we did not have enough time.
   - pair real-time customers with workers, we currently only survice jobs in the future
     We wanted to have workers be able to "look for work now" and get paired using priority queues with customers "looking for workers        now". The logic is completed in the backend, but we did not have time to implement this in the activities and the HTTP interface. 


## Meeting Highlights

Going into the next iteration, our main insights are:

 Pair programming worked really well. We got the most work done during long sessions on campus with all active members present. Having high comminication and asking each other questions helps improve everyone's understanding and solve problems quickly. 
 
 We need to communicate more with non-active members and make sure to motivate everyone. We want everyone to be motivated and believe that by enabling members to output their best work everyone is benefitted. 
 
 We need to plan around unexpected changes and lost time by having better contingency planning and working on items much earlier in the sprint. Overall, being more proactive is something we want everyone to work on. It can be difficult during the last few weeks of school, when everyone has many projects to work on, but dedicating time into organizing the team early on would have gone a long way during this iteration cycle.  
