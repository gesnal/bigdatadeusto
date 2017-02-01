students = load 'students.csv' using PigStorage(',') 
AS (Sex:chararray,Major:chararray,Major2:chararray,Major3:chararray,Level:chararray,Brothers:int,Sisters:int,BirthOrder:int,MilesHome:float,MilesLocal:float,Sleep:chararray,BloodType:chararray,Height:float);

height_by_sex = group students by Sex;

max_height_by_sex = foreach height_by_sex generate group, MAX(students.Height) as height;

dump max_height_by_sex;
