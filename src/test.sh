#!/bin/bash

make
CURRENTDIR="$(pwd)"
TESTDIR="$CURRENTDIR/test\ cases"
BINARY = "a.out"

for args;
do
	#checks if logs folder has been created. If not, it is created
	if[! -d $TESTDIR/logs/$args]; then
		mkdir -p $TESTDIR/logs/$args;
	fi
	# gets the count of the number of tests to be run
	test_count = "$(ls $TESTDIR/input| wc -l)"
	#backs up current transaction logs
	cp $CURRENTDIR/Transaction/Daily/daily.trans $CURRENTDIR/backup
	cp $CURRENTDIR/Transaction/Users/current.trans $CURRENTDIR/backup
	cp $CURRENTDIR/Transaction/Tickets/avail.trans $CURRENTDIR/backup
	for((i=1; i < $test_count; i++));
	do
		# copies empty/default transaction files
		cp $CURRENTDIR/empty_trabsactionfile.trans $CURRENTDIR/Transaction/Daily/daily.trans
		cp $CURRENTDIR/default_current.trans $CURRENTDIR/Transaction/Users/current.trans
		cp $CURRENTDIR/empty_trabsactionfile.trans $CURRENTDIR/Transaction/Tickets/avail.trans
		# run the test case and send output to a log file
		"./$BINARY" < "$TESTDIR/$args/input/$args$i.in" &> "$TESTDIR/logs/$args/$args$i.out"
		# copy current transaction files to a test folder
		cp $CURRENTDIR/Transaction/Daily/daily.trans $TESTDIR/logs/$args/trans/daily$i.trans
		cp $CURRENTDIR/Transaction/Users/current.trans $TESTDIR/logs/$args/trans/current$i.trans
		cp $CURRENTDIR/Transaction/Tickets/avail.trans $TESTDIR/logs/$args/trans/avail$i.trans
		# find the difference between the outputted transaction files and expected transaction files
		diff --suppress-common-lines $TESTDIR/logs/$args/$args$i.out $TESTDIR/$args/output/$args$i.out > "$TESTDIR/logs/$args/$args"Test"$i.dtf"
		diff --suppress-common-lines $CURRENTDIR/Transaction/Daily/daily.trans $TESTDIR/$args/transaction\ output/$args$i.trans > "$TESTDIR/logs/$args/$args"Test"$i.dtf"
		# display the current test being run
		echo Running "$arg"Test "$i"
	done
	# copy backed up transaction files back to original location
	cp $CURRENTDIR/backup/daily.trans $CURRENTDIR/Transaction/Daily/daily.trans 
	cp $CURRENTDIR/backup/current.trans $CURRENTDIR/Transaction/Users/current.trans
	cp $CURRENTDIR/backup/avail.trans $CURRENTDIR/Transaction/Tickets/avail.trans
done
#displays when the tests are complete
echo "Test complete"
