@echo off
echo Starting JMeter Load Test for LoanCore...
rem JMeter bin 경로를 시스템 환경변수에 맞게 수정하거나 전체 경로를 입력하세요.
jmeter -n -t jmeter\LoanCore_TestPlan.jmx -l jmeter\test_results.jtl -e -o jmeter\html_report
echo Test Completed. Check jmeter\html_report folder for results.
pause