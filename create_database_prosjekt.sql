-- oppretter databasen til Viveka, Daniel og August

create table Course (
	courseID char(7), /* format: TDT4145 */
    _Name varchar(45) not null,
    term char(5), /* format: V2020, H2020 */ 
    anonymous bool,
    inviteCode varchar(10) not null,
    constraint Course_pk primary key (courseID) 
					) ;
                    
create table _User (
	email varchar(40),
    password varchar(30) not null,
    role varchar(30) not null, 
    constraint User_pk primary key (email)
					) ;
                    
 create table Enrolled (
	email varchar(40),
    courseID char(7),
    constraint Enrolled_pk primary key (email, courseID),
    constraint Enrolled_fk_User foreign key (email) 
				references _User (email)
                on update cascade
                on delete cascade,
	constraint Enrolled_fk_Course foreign key (courseID)
				references Course (courseID)
                on update cascade
                on delete cascade
					) ;
                    
create table Folder (
	folderID int,  /* generert nøkkel */
	courseID char(7),
    _name varchar(30),
    constraint Folder_pk primary key (folderID),
    constraint Folder_fk_Course foreign key (courseID)
				references Course (courseID)
                on update cascade
                on delete cascade /* dersom emnet slettes, finnes ikke mappen lengre*/
					) ;
                    
create table Post (
	postID int,
    title varchar(30) not null,
    author varchar (30),
    content varchar(100),
    courseID char(7),
    _type enum('Homework','Note','Announcement','Comment', 'Question', 'Exam') not null, 				/*osv*/
    colorCode enum('red', 'green', 'yellow') not null,
    creatorEmail varchar(40),
    constraint Post_pk primary key (postID),
    constraint Post_fk_User foreign key (creatorEmail) 
				references _User(email)
                on update cascade
                on delete cascade,
	constraint Post_fk_Course foreign key (courseID)
				references Course (courseID)
                on update cascade
                on delete cascade
					) ;
                    
create table LinkToPost ( 
	postID int,
    LinkedPostID int,
    constraint LinkToPost_pk primary key (postID, LinkedPostID),
    constraint LinkToPost_fk_Post foreign key (PostID) 
				references Post (postID) 
					on update cascade
                    on delete cascade, /* dersom posten slettes kan den ikke lenger linke til en annen post*/
	constraint LinkToPost_fk_linkedPost foreign key (LinkedPostID)
				references Post (postID)
					on update cascade
                    on delete cascade
                    ) ;
                    
create table StudentsAnswer (
	postID int,
    content varchar(100),
    constraint StudentsAnswer_pk primary key (postID),
    constraint StudentsAnswer_fk_Post foreign key (postID)
				references Post (postID)
                on update cascade
                on delete cascade
					) ;
                    
create table InstructorsAnswer (
	postID int,
    content varchar(100),
    constraint InstructorsAnswer_pk primary key (postID),
    constraint InstructorsAnswer_fk_Post foreign key (postID)
				references Post (postID)
                on update cascade
                on delete cascade
					) ;

create table AnsweredByStudent (
	postID int,
    email varchar(40),
    _Date date,
    constraint AnsweredByStudent_pk primary key (postID, email),
    constraint AnsweredByStudent_fk_User foreign key (email)
				references _User (email)
                on update cascade
                on delete no action,
	constraint AnsweredByStudent_fk_Post foreign key (postID)
				references Post (postID)
                on update cascade
                on delete cascade
					) ;

create table AnsweredByInstructor (
	postID int,
    email varchar(40),
    _Date date,
    constraint AnsweredByInstructor_pk primary key (postID, email),
    constraint AnsweredByInstructor_fk_User foreign key (email)
				references _User (email)
                on update cascade
                on delete no action,
	constraint AnsweredByInstructor_fk_Post foreign key (postID)
                references Post (postID)
                on update cascade
                on delete cascade
					) ;

create table HasRead (
	postID int,
    email varchar(40),
    _Date date,
    constraint HasRead_pk primary key (postID, email),
    constraint HasRead_fk_Post foreign key (postID)
				references Post (postID)
                on update cascade
                on delete no action,
	constraint HasRead_fk_User foreign key (email)
				references _User (email)
                on update cascade
                on delete cascade
					) ;
                    
create table LikedPost (
	email varchar (40),
    postID int,
    constraint LikedPost_pk primary key (email, postID),
    constraint LikedPost_fk_User foreign key (email)
				references _User(email)
                on update cascade
                on delete cascade,
	constraint LikedPost_fk_Post foreign key (postID)
				references Post(postID)
                on update cascade
                on delete cascade
					) ;

create table PostInFolder (
	postID int,
    folderID int,
    constraint PostInFolder_pk primary key (postID, folderID),
    constraint PostInFolder_fk_Post foreign key (postID)
				references Post (postID)
                on update cascade
                on delete cascade,
	constraint PostInFolder_fk_Folder foreign key (folderID)
				references Folder (folderID)
                on update cascade
                on delete no action /* post må være i en folder, og kan derfor ikke slette folders som inneholder posts.*/
					) ;
                
create table FollowUp (
	followUpNr int, #generert nøkkel
    postID int,
    author varchar(30),
    content varchar(100),
    creatorEmail varchar(40),
    _Date date,
    constraint FollowUp_pk primary key (followUpNr),
    constraint FollowUp_fk_Post foreign key (postID)
				references Post (postID)
                on update cascade
                on delete cascade,
    constraint FollowUp_fk_User foreign key (creatorEmail) 
				references _User (email)
                on update cascade
                on delete set null
					) ;

create table _Comment (
	commentNr int,
    followUpNr int,
    author varchar(30),
    content varchar(100),
    creatorEmail varchar(40),
    constraint Comment_pk primary key (commentNr),
    constraint Comment_fk_User foreign key (creatorEmail)
				references _User (email)
                on update cascade
                on delete set null,
    constraint Comment_fk_FollowUp foreign key (followUpNr)
				references FollowUp (followUpNr)
                on update cascade
                on delete cascade
					) ;
                    
					
INSERT INTO Course VALUES('TDT4145', 'Datamodellering og databasesystemer', 'V2021', true, 12345);
INSERT INTO _User VALUES('brukernavn@gmail.com', 'passord', 'Student');
INSERT INTO Folder VALUES('1', 'TDT4145', 'Exam');