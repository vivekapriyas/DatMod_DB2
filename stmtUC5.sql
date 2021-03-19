select email,count(postID) as NumberRead, NumberPosted
from (hasread right outer join (select * from enrolled where courseid = 'TMA4180') courseusers  using(email))
inner join 
(select email, count(postID) as NumberPosted
from post right outer join (select * from enrolled where courseid = 'TMA4180') courseusers on creatoremail = email
group by email) as Posted using(email)
group by email
order by NumberRead desc,NumberPosted desc;
