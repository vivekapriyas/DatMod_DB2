insert into course values('TMA4180','Optimering','V2021',true,'passord123');

insert into _user values('stein@ntnu.no','steinspassord','Instructor'),
('lisa@ntnu.no','lisaspassord','Student'),('anders@ntnu.no','anderspassord','Student');

insert into enrolled values('stein@ntnu.no','TMA4180'),('lisa@ntnu.no','TMA4180'),('anders@ntnu.no','TMA4180');

insert into folder values (1,'TMA4180','Exam'),(2,'TMA4180','Project');

insert into post values (1,'Post 1','Anonymous','Her et sted er ordet WAL gjemt','TMA4180','Question','green','lisa@ntnu.no','2021-02-27'),
(2,'Post 2 om WAL','Anonymous','Her er det ikke gjemt','TMA4180','Question','red','lisa@ntnu.no','2021-03-01'),
(3,'Post 3','anders@ntnu.no','Her er det heller ikke gjemt','TMA4180','Question','yellow','anders@ntnu.no','2021-03-08');

insert into postinfolder values(1,1),(1,2),(2,1),(3,2);

insert into studentsanswer values ('1','Her er et svar som inneholder WAL');
insert into answeredbystudent values (1,'anders@ntnu.no','2021-03-02');

insert into instructorsanswer values(3,'WAL er gjemt i dette svaret og');
insert into answeredbyinstructor values (3,'stein@ntnu.no','2021-03-08');

insert into followup values (1,1,'Anonymous','her er ikke noe gjemt','lisa@ntnu.no','2021-03-02'),
(2,3,'Anonymous','her derimot har vi gjemt WAL','lisa@ntnu.no','2021-03-10');

insert into _comment values(1,1,'stein@ntnu.no','denne kommentaren har WAL i seg','stein@ntnu.no'),
(2,1,'anders@ntnu.no','igjen er WAL gjemt','anders@ntnu.no'),
(3,2,'stein@ntnu.no','her er ingenting gjemt','stein@ntnu.no'),
(4,2,'Anonymous','WAL','lisa@ntnu.no');

insert into post values (4,'Post 4','Anonymous','denne posten skal ikke dukke opp','TMA4180','Question','red','lisa@ntnu.no','2021-03-11');
insert into postinfolder values (4,1);
insert into followup values(3,4,'anders@ntnu.no','ikke noe å se her','anders@ntnu.no','2021-03-12');
insert into _comment values (5,3,'stein@ntnu.no','igjen, her er det ingenting','stein@ntnu.no');


insert into hasread values (1,'anders@ntnu.no','2021-03-02'),(1,'lisa@ntnu.no','2021-03-02'),(1,'stein@ntnu.no','2021-03-04'),
(3,'lisa@ntnu.no','2021-03-10'),(3,'stein@ntnu.no','2021-03-08'),(4,'stein@ntnu.no','2021-03-14'),(4,'anders@ntnu.no','2021-03-12');

insert into _user values ('ole@ntnu.no','olespassord','student');
insert into enrolled values('ole@ntnu.no','TMA4180');

insert into course values ('TDT4145','Datamodeling','V2021',false,'passord000');
insert into _user values ('marie@ntnu.no','mareispassord','student');
insert into enrolled values('marie@ntnu.no','TDT4145');
