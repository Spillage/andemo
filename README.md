# ReadMe

## How to run the project
- mvn clean testgi

## Some conclusions

- It seems like not all APIs share the same filter or interceptor for key checking.
  - For example: 
    - /api/{culture}/collection
    - /api/{culture}/{object-number}
    - /api{culture}/collection/{object-number}/tiles
    - The response message is "Invalid key".
  - But for following:
    - /api/{culture}/usersets
    - /api/{culture}/usersets/{set-id}
    - The response message is "An invalid key is provided".

- It seems the RijksMuseum website is using AzureCDN.

- The entire website is not separated from the front and back ends
- By going through the performance test of the website, every 10 times of request got a response time almost 300 ms, and the others in almost 30 ms.
- For '/api/{culture}/{object-number}':
  - When search with a certain object number, it returns all collection objects but not the requested one, I think it is not expected.(fixed, check on 21/1/2025)
  - When search with an invalid object number, it returns all collection objects but not the requested one, I think it is not expected.
- For '/api{culture}/collection/{object-number}/tiles'
  - When search with an invalid object number, it returns 403, it is better to return 404 or a "not found" message.
- For '/api/{culture}/usersets'
  - When request with different page\*pageSize(20\*3 & 20\*5), almost returns the same result, it is weird. 
  - But if the parameter is 3\*3 or 4\*3, it returns correctly. I think there are rooms for the API to update.
  - When request with invalid page*pageSize(4\*5000), the response is random *pageSize* usersets, I think it is better to return 0.
  - When request with invalid page*pageSize(2\*10000), the response is 0, it is different from the previous one.
- Why does the collection APIs return in JSON format but the usersets APIs return in XML format?
  - (Updated on 21:30 20/1/2025), it seems the IT team has fixed this issue, the API return in JSON format now.)
- (Updated on 21:30 20/1/2025)It looks like the IT team of RijksMuseum is working on issues fixing, I need to keep updating my code.
- (Updated on 10:30 21/1/2025) It looks like the verbs"Identity" changed to "Identify" on the document.
