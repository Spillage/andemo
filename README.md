# ReadMe

## Some conclutions

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
  - When search with a certain object number, it returns all collection objects but not the requested one, I think it is not expected.
  - When search with an invalid object number, it returns all collection objects but not the requested one, I think it is not expected.
- For '/api{culture}/collection/{object-number}/tiles'
  - When search with an invalid object number, it returns 1 which is meaningless.
- For '/api/{culture}/usersets'
  - When request with different page\*pageSize(20\*3 & 20\*5), the first user is always the sender itself and then others, it is weird. 
  - When request with invalid page*pageSize(4\*5000), the response is random *pageSize* usersets, I think it is better to return 0.
  - When request with invalid page*pageSize(2\*10000), the response is 0, it is different from the previous one.
- Why does the collection APIs return in JSON format but the usersets APIs return in XML format?
