#!/bin/sh

for n in $@
do
	for i in 1 2 3 4
	do
		#nn=$(echo "${n:0:1}${n:2:1}${n:3:1}${n:4:1}${n:1:1}${n:5:4}")
		nn=$(echo "${n:0:1}${n:4:1}${n:1:1}${n:2:1}${n:3:1}${n:5:4}")
		if [ !  -e "$nn" ]
		then
			echo "rotate... $n -> $nn"
			convert -rotate 90  "$n"  "$nn"
		fi
		n=$nn
	done	
done
