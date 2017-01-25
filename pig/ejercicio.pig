measure = load 'data.csv' using PigStorage(',') 
AS (date:chararray, ra:float, hu:float, ws:float, ba:float, te:float, en:float);
 
filter_measure = filter measure by date != 'fecha';
	
measure_by_windspeed = group filter_measure by ws;

num_measures_by_windspeed = foreach measure_by_windspeed generate group, AVG(filter_measure.en) as measure;

dump num_measures_by_windspeed;
