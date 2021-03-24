select email,count(postID) as NumberRead, NumberPosted
from (hasread right outer join (select * from (select * from enrolled right outer join _user using(email) where role = 'Student') as students where courseid = 'TMA4180') courseusers  using(email))
inner join 
(select email, count(postID) as NumberPosted
from post right outer join (select * from enrolled where courseid = 'TMA4180') courseusers on creatoremail = email
group by email) as Posted using(email)
group by email;

