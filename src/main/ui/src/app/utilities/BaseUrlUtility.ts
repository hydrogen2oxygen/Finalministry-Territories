import {environment} from "../../environments/environment";

export class BaseUrlUtility {

  static getBaseUrl():string {

    let baseUrl = window.location.origin.substr(0,window.location.origin.lastIndexOf(":"));

    if (!environment.production) {
      baseUrl += ":" + environment.serverPort;
    }

    return baseUrl;
  }
}
