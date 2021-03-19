select distinct postID
from post left outer join (_comment inner join followup using(followupnr)) using (postID)
where title like '%WAL%' or followup.content like '%WAL%' or post.content like '%WAL%';
