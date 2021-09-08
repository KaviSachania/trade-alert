# Trade Alert

Email service and accompanying web application for sending customizable weekly email alerts of stocks that have recently reached a price around a proven support line.


- Java Spring REST service fetches stock price data from FinnHub API and analyzes if a stock's price is at a support line that has been tested multiple times.
- React web application that renders the list of stocks and allows logged in users to create and delete email alert triggers.
- PostgreSQL database for storing stocks, prices, alerts, and users.
- AWS Simple Email Service for sending customizable weekly email alerts of stocks that recently reached their support line.
- Hosted on AWS ElasticBeanstalk (EBS), Simple Cloud Storage (S3), and Relational Database Service (RDS).



![demo](tradealert-demo.gif)


![diagram](tradealert-diagram.jpg)






