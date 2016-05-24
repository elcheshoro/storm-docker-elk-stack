# Storm docker ELK stack example

This is a skeleton project running the tutorial storm topology WordCount on docker machines.
It integrates with the ELK stack to display logs from the storm topology in Kibana

## Prerequisites
To run this project you need maven installed to package the Java and Vagrant and Virtual box installed and configured.

To set up vagrant and virtual box you can follow this guide https://www.vagrantup.com/docs/getting-started/

## Set up
1. clone this repository
2. Navigate to WordCount and run mvn clean package to build the topology jar with dependencies
3. from the root folder run vagrant up
4. If the topology fails to start run vagrant provision until it does (needs fixing)
5. Once all the docker containers are successfully running on the VM you can import the dashboard configurations from /config/kibana/dashboard.json

## Team members
Alexander Cheshire