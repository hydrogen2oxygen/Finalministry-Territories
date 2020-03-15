export class UserUtility {

  static removeRole(roles:string, roleToRemove:string):string {

    let rolesParts: string[] = roles.split(",");
    roles = "";

    for (let role of rolesParts) {

      if (role === roleToRemove) continue;

      if (roles.length > 0) roles += ",";

      roles += role;
    }

    return roles;
  }

  static addRole(roles:string, roleToAdd:string):string {

    if (roles.indexOf(roleToAdd) >= 0) return roles;
    if (roles.length === 0) return roleToAdd;

    return roles + "," + roleToAdd;
  }
}
