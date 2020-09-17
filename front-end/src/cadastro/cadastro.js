





function registerUser(){
  let nome = document.getElementById('name').value
  let email = document.getElementById('email').value
  let data = document.getElementById('date').value
  let senha = document.getElementById('password').value
  let confirmacao = document.getElementById('confirm').value
  
  if (validaSenha()){
    alert("As senha precisam ser iguais, por favor verifique!")

  }
}

function validaSenha() {
  let senha = document.getElementById('password').value
  let confirmacao = document.getElementById('confirm').value
  if (senha.localeCompare(confirmacao)){
    return true
  }
  else {
    return false
  }
}


function validaEmail(field) {
usuario = field.value.substring(0, field.value.indexOf("@"));
dominio = field.value.substring(field.value.indexOf("@")+ 1, field.value.length);

if ((usuario.length >=1) &&
    (dominio.length >=3) &&
    (usuario.search("@")==-1) &&
    (dominio.search("@")==-1) &&
    (usuario.search(" ")==-1) &&
    (dominio.search(" ")==-1) &&
    (dominio.search(".")!=-1) &&
    (dominio.indexOf(".") >=1)&&
    (dominio.lastIndexOf(".") < dominio.length - 1)) {
document.getElementById("email").innerHTML="E-mail válido";
}
else{
document.getElementById("email").innerHTML="<font color='red'>E-mail inválido </font>";
alert("E-mail invalido");
}
}