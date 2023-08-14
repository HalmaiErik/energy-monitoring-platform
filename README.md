# energy-monitoring-platform
Energy monitoring system storing consumption data from client's smart metering devices.
Includes:
1. energyplatform: Backend application built with Java and Spring Boot. Deployed on Heroku. Supports user login, registration, creation of new devices for a user, a new sensor for a device and adding sensor data to sensors.  Supports reading the sensor data from a RabbitMQ. Supports RPC calls from a Smart Appliance desktop app. Uses REST to expose data from a PostGreSQL database to the frontend.
2. energyplatform-frontend: Frontend application of the energy platform, built with Angular.
3. sensorsimulator: Simulates a sensor by reading values from a file and posting them to a RabbitMQ in order to be processed by the backend application
4. smartappliance: Desktop application with login feature, that draws charts, graphs from the monitored sensor data.
