#!/bin/bash

# Generate dummy rentals for flooding the database with rental data

account_id=3 # this the account named "test"

rental_id=1000

book_id=1
year_id=2000
month_id=10
day_id=15

max_book_id=20
max_year_id=2018
max_month_id=12
max_day_id=28


for YEAR in `seq $year_id $max_year_id`; do
	for MONTH in `seq $month_id $max_month_id`; do
		for DAY in `seq $day_id $max_day_id`; do
			for BOOK in `seq $book_id $max_book_id`; do
				echo "INSERT INTO RENTAL VALUES($account_id, $BOOK, $rental_id, '$YEAR-$MONTH-$DAY', '$YEAR-$MONTH-$DAY', false);"
				rental_id=$(($rental_id+1))
			done
		done
	done
done

