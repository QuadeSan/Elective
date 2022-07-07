function showPass() {
  var x = document.getElementById("psw");
  if (x.type === "password") {
    x.type = "text";
  } else {
    x.type = "password";
  }
}

function confirmAlert() {
    var confirmAction = confirm("Are you sure you want to perform this action?");
    if (confirmAction) {
         return true;
    } else {
         return false;
    }
}

function lastPage() {
    var lastPage = document.referrer;
    return lastPage;
}