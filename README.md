# Restaurant
This application is designed to support a restaurant to manage their menu and bill order. User can interact with server via RESTful web service using Swagger UI tool

# Supported Functions
## Manage Retaurant Menu
   1. List all menu items of restautant - <i>support pagination</i>
   2. Create/Update/Delete a menu item
   3. Search menu(s) via title, description or additinal details - <i>support pagination</i>
## Manage Bill
   1. Create/Update a bill 
   2. Retrieve information of given bill with number of menu items, and total prices
# Prerequisite
   1. A MySQL Server instance - <i>could use Docker container or standalone installation</i>
   2. Connect to MySQL server, and create a schema: <b>restaurant</b>
   3. (Optional) initialize some data if needed with following script<br>
  ```
  INSERT INTO restaurant.menu_item (name, description, image_url, price, details) VALUES
('Hawaiian Pizza', 'All-time favourite toppings, Hawaiian pizza in Tropical Hawaii style.','https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu1.jpg', 300, 'Italian Ham Pineapple'),
('Chicken Tom Yum Pizza', 'Best marinated chicken with pineapple and mushroom on Spicy Lemon sauce. Enjoy our tasty Thai style pizza.', 'https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu2.jpg', 350, 'Italian Thai Chicken Mushroom Hot'),
('Xiaolongbao', 'Chinese steamed bun', 'https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu3.jpg', 200, 'Chinese Pork Recommended'),
('Kimchi', 'Traditional side dish made from salted and fermented vegetables', 'https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu4.jpg', 50, 'Korean Radish Cabbage'),
('Oolong tea', 'Partially fermented tea grown in the Alishan area', 'https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu5.jpg', 30, 'Hot Non-alcohol'),
('Beer', 'Fantastic flavors and authentic regional appeal beer', 'https://s3-ap-southeast-1.amazonaws.com/interview.ampostech.com/backend/restaurant/menu6.jpg', 60, 'Alcohol');
 ```
 # Usage
 Access Swagger UI via <a href="url">http://<ip_address>:8080/swagger-ui.html#/</a> to interact with server.
  

