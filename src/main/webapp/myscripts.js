function showPass() {
  var x = document.getElementById("psw");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}

function confirmAlert() {
    var confirmAction = confirm("Are you sure to execute this action?");
    if (confirmAction) {
         return true;
    } else {
         return false;
    }
}