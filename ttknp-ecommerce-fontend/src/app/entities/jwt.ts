/*{
  "jwt": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGl0aWtvcm4tbkBybXV0cC5hYy50aCIsImV4cCI6MTcyMjcwMzY5OCwiaWF0IjoxNzE5MTAzNjk4fQ.thXyDJ_htmOOWmjKfkr37Dm6ZHmFwXmlyvNMa3WkUP2nMnKiBU8JLFieYLthYgaj5tw1h2L-Q5zvJwLhnwmI2Q",
  "username": "thitikorn-n@rmutp.ac.th"
}*/
export class Jwt {
  public jwt: string;
  public username:string

  constructor(jwt: string, username: string) {
    this.jwt = jwt;
    this.username = username;
  }
}
