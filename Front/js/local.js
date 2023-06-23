const urlApi = "http://localhost:3000";

async function login() {
    var myForm = document.getElementById("loginForm");
    var formData = new FormData(myForm);
    var jsonData = {};
    for (var [k, v] of formData) {
        jsonData[k] = v;
    }

    const request = await fetch(urlApi + "/auth/login", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    });

    const response = await request.json();
    console.log(response.data.token);
    console.log(request.status);
    if (request.status === 200) {
        localStorage.token = response.data.token;

        localStorage.email = jsonData.email;     
        location.href= "dashboard.html";
    }

}

function listar() {
    const tablaSuperior = `
    <table class="table">
      <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">First Name</th>
          <th scope="col">Last Name</th>
          <th scope="col">Email</th>
          <th scope="col">Address</th>
          <th scope="col">Birthday</th>
          <th scope="col">Action</th>
        </tr>
      </thead>
      <tbody id="listar"></tbody>
    </table>
`;

        document.getElementById("table").innerHTML = tablaSuperior;
        validaToken();
        var settings = {
            method: 'GET',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Authorization': localStorage.token
            },
        };

        fetch(urlApi + "/user", settings)
            .then(response => response.json())
            .then(function(response) {
                var usuarios = '';
                for (const usuario of response.data) {
                    console.log(usuario.mail);
                    usuarios += `
                        <tr>
                            <th scope="row">${usuario.id}</th>
                            <td>${usuario.name}</td>
                            <td>${usuario.lastname}</td>
                            <td>${usuario.mail}</td>
                            <td>${usuario.address}</td>
                            <td>${usuario.birthday}</td>
                            <td>
                                <button type="button" class="btn btn-outline-danger" onclick="eliminaUsuario('${usuario.id}')">
                                    <i class="fa-solid fa-user-minus"></i>
                                </button>
                                <a href="#" onclick="verModificarUsuario('${usuario.id}')" class="btn btn-outline-warning">
                                    <i class="fa-solid fa-user-pen"></i>
                                </a>
                                <a href="#" onclick="verUsuario('${usuario.id}')" class="btn btn-outline-info">
                                    <i class="fa-solid fa-eye"></i>
                                </a>
                            </td>
                        </tr>`;
                }
                document.getElementById("listar").innerHTML = usuarios;
            });
}


function listar2() {
    validaToken();
    var titulo = document.getElementById('listadosTitle');

    titulo.textContent = 'Listado de Productos';
    var xhr = new XMLHttpRequest();
    xhr.open('GET', urlApi + "/products", true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', localStorage.token);

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 400) {
            var response = JSON.parse(xhr.responseText);
            console.log(response)

            const tablaSuperior = `
        <table class="table">
          <thead>
          <tr>
          <th scope="row">id</th>
          <th scope="row">title</td>
          <th scope="row">price</td>
          <th scope="row">category</td>
          <th scope="row">description</td>
          <th scope="row">image</td>
          <th scope="row">rate</td>
          <th scope="row">count</td>
          </tr>
          </thead>
          <tbody id="listar">
      
          </tbody>
        </table>
      `;
            document.getElementById("table").innerHTML = tablaSuperior;

            let productos = '';

            for (const producto of response.data) {
                productos += `
            <tr>
            <th scope="row">${producto.id}</th>
            <td>${producto.title}</td>
            <td>${producto.price}</td>
            <td>${producto.category}</td>
            <td>${producto.description}</td>
            <td>${producto.image}</td>
            <td>${producto.rating.rate}</td>
            <td>${producto.rating.count}</td>
            <td>
                <button type="button" class="btn btn-outline-danger" onclick="eliminaProducto('${producto.id}')">
                <i class="fa-solid fa-user-minus"></i>
                </button>
                <a href="#" onclick="verModificarProducto('${producto.id}')" class="btn btn-outline-warning">
                <i class="fa-solid fa-user-pen"></i>
                </a>
                <a href="#" onclick="verProducto('${producto.id}')" class="btn btn-outline-info">
                <i class="fa-solid fa-eye"></i>
                </a>
            </td>
            </tr>`;
            }
            document.getElementById("listar").innerHTML = productos;

            var elemento = document.querySelector('.btn-outline-success');

            elemento.addEventListener('click', registerForm2);


        } else {}
    };

    xhr.onerror = function() {};

    xhr.send();
}


function eliminaUsuario(id) {
    validaToken();
    var settings = {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        },
    }
    fetch(urlApi + "/user/" + id, settings)
        .then(response => response.json())
        .then(function(data) {
            listar();
            alertas("Se ha eliminado el usuario exitosamente!", 2)
        })
}

function verModificarUsuario(id) {
    validaToken();
    var settings = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        },
    }
    fetch(urlApi + "/user/" + id, settings)
        .then(response => response.json())
        .then(function(response) {
            var cadena = '';
            const usuario = response.data;
            if (usuario) {
                cadena = `
                <div class="p-3 mb-2 bg-light text-dark">
                    <h1 class="display-5"><i class="fa-solid fa-user-pen"></i> Modificar Usuario</h1>
                </div>
              
                <form action="" method="post" id="updateForm">
                    <input type="hidden" name="id" id="id" value="${usuario.id}">

                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" name="firstName" id="firstName" required value="${usuario.firstName}"> <br>

                    <label for="lastName"  class="form-label">Last Name</label>
                    <input type="text" class="form-control" name="lastName" id="lastName" required value="${usuario.lastName}"> <br>

                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" name="email" id="email" required value="${usuario.email}"> <br>

                    <label for="address" class="form-label">Address</label>
                    <input type="address" class="form-control" name="address" id="address" required value="${usuario.address}"> <br>

                    <label for="birthday" class="form-label">Birthday</label>
                    <input type="birthday" class="form-control" name="birthday" id="birthday" required value="${usuario.birthday}"> <br>

                    <label for="password" class="form-label">Password</label>
                    <input type="password" class="form-control" id="password" name="password" required> <br>
                    <button type="button" class="btn btn-outline-warning" 
                        onclick="modificarUsuario('${usuario.id}')">Modificar
                    </button>
                </form>`;
            }
            document.getElementById("contentModal").innerHTML = cadena;
            var myModal = new bootstrap.Modal(document.getElementById('modalUsuario'))
            myModal.toggle();
        })
}

async function modificarUsuario(id) {
    validaToken();
    var myForm = document.getElementById("updateForm");
    var formData = new FormData(myForm);
    var jsonData = {};
    for (var [k, v] of formData) {
        jsonData[k] = v;
    }
    const request = await fetch(urlApi + "/user/" + id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(jsonData)
    });
    listar();
    alertas("Se ha modificado el usuario exitosamente!", 1)
    document.getElementById("contentModal").innerHTML = '';
    var myModalEl = document.getElementById('modalUsuario')
    var modal = bootstrap.Modal.getInstance(myModalEl)
    modal.hide();
}

function verUsuario(id) {
    validaToken();
    var settings = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        },
    }
    fetch(urlApi + "/user/" + id, settings)
        .then(response => response.json())
        .then(function(response) {
            var cadena = '';
            const usuario = response.data;
            if (usuario) {
                cadena = `
                <div class="p-3 mb-2 bg-light text-dark">
                    <h1 class="display-5"><i class="fa-solid fa-user-pen"></i> Visualizar Usuario</h1>
                </div>
                <ul class="list-group">
                    <li class="list-group-item">Nombre: ${usuario.firstName}</li>
                    <li class="list-group-item">Apellido: ${usuario.lastName}</li>
                    <li class="list-group-item">Correo: ${usuario.email}</li>
                    <li class="list-group-item">Direccion: ${usuario.address}</li>
                    <li class="list-group-item">Fecha: ${usuario.birthday}</li>
                </ul>`;

            }
            document.getElementById("contentModal").innerHTML = cadena;
            var myModal = new bootstrap.Modal(document.getElementById('modalUsuario'))
            myModal.toggle();
        })
}

function alertas(mensaje, tipo) {
    var color = "";
    if (tipo == 1) {
        color = "success"
    } else {
        color = "danger"
    }
    var alerta = `<div class="alert alert-${color} alert-dismissible fade show" role="alert">
                    <strong><i class="fa-solid fa-triangle-exclamation"></i></strong>
                        ${mensaje}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                 </div>`;
    document.getElementById("datos").innerHTML = alerta;
}

function registerForm() {
    cadena = `
            <div class="p-3 mb-2 bg-light text-dark">
                <h1 class="display-5"><i class="fa-solid fa-user-pen"></i> Registrar Usuario</h1>
            </div>
              
            <form action="" method="post" id="myForm">
                <input type="hidden" name="id" id="id">

                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" name="firstName" id="firstName" required> <br>

                <label for="lastName"  class="form-label">Last Name</label>
                <input type="text" class="form-control" name="lastName" id="lastName" required> <br>

                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" name="email" id="email" required> <br>

                <label for="address" class="form-label">Address</label>
                <input type="address" class="form-control" name="address" id="address" required> <br>

                <label for="birthday" class="form-label">Birthday</label>
                <input type="birthday" class="form-control" name="birthday" id="birthday" required> <br>

                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required> <br>
                <button type="button" class="btn btn-outline-info" onclick="registrarUsuario()">Registrar</button>
            </form>`;
    document.getElementById("contentModal").innerHTML = cadena;
    var myModal = new bootstrap.Modal(document.getElementById('modalUsuario'))
    myModal.toggle();
}

async function registrarUsuario() {
    var myForm = document.getElementById("myForm");
    var formData = new FormData(myForm);
    var jsonData = {};
    for (var [k, v] of formData) {
        jsonData[k] = v;
    }
    const request = await fetch(urlApi + "/user", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    });
    listar();
    alertas("Se ha registrado el usuario exitosamente!", 1)
    document.getElementById("contentModal").innerHTML = '';
    var myModalEl = document.getElementById('modalUsuario')
    var modal = bootstrap.Modal.getInstance(myModalEl)
    modal.hide();
}

function modalConfirmacion(texto, funcion) {
    document.getElementById("contenidoConfirmacion").innerHTML = texto;
    var myModal = new bootstrap.Modal(document.getElementById('modalConfirmacion'))
    myModal.toggle();
    var confirmar = document.getElementById("confirmar");
    confirmar.onclick = funcion;
}

function salir() {
    localStorage.clear();
    location.href = "index.html";
}

function validaToken() {
    if (localStorage.token == undefined) {
        salir();
    }
}


function verModificarProducto(id) {
    validaToken();
    var xhr = new XMLHttpRequest();
    xhr.open('GET', urlApi + "/products", true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', localStorage.token);

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 400) {
            var response = JSON.parse(xhr.responseText);
            console.log(response)
            var cadena = '';
            const producto = response.data;
            if (producto) {
                cadena = `
                <div class="p-3 mb-2 bg-light text-dark">
                    <h1 class="display-5"><i class="fa-solid fa-user-pen"></i> Modificar Producto</h1>
                </div>
              
                <form action="" method="post" id="updateForm">
                    <input type="hidden" name="id" id="id" value="${producto.id}">

                    <label for="title" class="form-label">Title</label>
                    <input type="text" class="form-control" name="title" id="title" required value="${producto.title}"> <br>

                    <label for="price"  class="form-label">Price</label>
                    <input type="text" class="form-control" name="price" id="price" required value="${producto.price}"> <br>

                    <label for="category" class="form-label">Category</label>
                    <input type="text" class="form-control" name="category" id="category" required value="${producto.category}"> <br>

                    <label for="description" class="form-label">Description</label>
                    <input type="text" class="form-control" name="description" id="description" required value="${producto.description}"> <br>

                    <label for="image" class="form-label">Image</label>
                    <input type="text" class="form-control" name="image" id="image" required value="${producto.image}"> <br>

                    <label for="ratingRate" class="form-label">Rating Rate</label>
                    <input type="text" class="form-control" name="ratingRate" id="ratingRate" required value="${producto.rating.rate}"> <br>

                    <label for="ratingCount" class="form-label">Rating Count</label>
                    <input type="text" class="form-control" name="ratingCount" id="ratingCount" required value="${producto.rating.count}"> <br>

                    <button type="button" class="btn btn-outline-warning" onclick="modificarProducto('${producto.id}')">Modificar</button>
                </form>`;
                document.getElementById("contentModal2").innerHTML = cadena;
                var myModal = new bootstrap.Modal(document.getElementById('modalProducts'));
                //myModal.toggle();
            }
        }
    };
}

async function modificarProducto(id) {
    validaToken();
    var myForm = document.getElementById("updateForm");
    var formData = new FormData(myForm);
    var jsonData = {};
    for (var [k, v] of formData) {
        jsonData[k] = v;
    }
    console.log(jsonData)
    const request = await fetch(urlApi + "/products/" + id, {
        method: 'PUT',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        },
        body: JSON.stringify(jsonData)
    });
    listarProductos();
    alertas("Se ha modificado el producto exitosamente!", 1);
    document.getElementById("contentModal").innerHTML = '';
    var myModalEl = document.getElementById('modalUsuario');
    var modal = bootstrap.Modal.getInstance(myModalEl);
    modal.hide();
}

function verProducto(id) {
    validaToken();
    var settings = {
        method: 'GET',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
            'Authorization': localStorage.token
        },
    };
    fetch(urlApi + "/products/" + id, settings)
        .then(response => response.json())
        .then(function(response) {
            var cadena = '';
            const producto = response.data;
            if (producto) {
                cadena = `
                <div class="p-3 mb-2 bg-light text-dark">
                    <h1 class="display-5"><i class="fa-solid fa-user-pen"></i> Visualizar Producto</h1>
                </div>
                <ul class="list-group">
                    <li class="list-group-item">Title: ${producto.title}</li>
                    <li class="list-group-item">Price: ${producto.price}</li>
                    <li class="list-group-item">Category: ${producto.category}</li>
                    <li class="list-group-item">Description: ${producto.description}</li>
                    <li class="list-group-item">Image: ${producto.image}</li>
                    <li class="list-group-item">Rating Rate: ${producto.rating.rate}</li>
                    <li class="list-group-item">Rating Count: ${producto.rating.count}</li>
                </ul>`;
            }
            document.getElementById("contentModal").innerHTML = cadena;
            var myModal = new bootstrap.Modal(document.getElementById('modalUsuario'));
            myModal.toggle();
        });
}

function registerForm2() {

    var titulo = document.getElementById('listadosTitle');


    titulo.textContent = 'Listado de Productos';
    var cadena = `
        <div class="p-3 mb-2 bg-light text-dark">
            <h1 class="display-5"><i class="fa-solid fa-user-pen"></i> Registrar Usuario</h1>
        </div>
        
        <form action="" method="post" id="myForm">
            <input type="hidden" name="id" id="id">

            <label for="title" class="form-label">Title</label>
            <input type="text" class="form-control" name="title" id="title" required> <br>

            <label for="price" class="form-label">Price</label>
            <input type="text" class="form-control" name="price" id="price" required> <br>

            <label for="category" class="form-label">Category</label>
            <input type="text" class="form-control" name="category" id="category" required> <br>

            <label for="description" class="form-label">Description</label>
            <input type="text" class="form-control" name="description" id="description" required> <br>

            <label for="image" class="form-label">Image</label>
            <input type="text" class="form-control" name="image" id="image" required> <br>

            <label for="rate" class="form-label">Rate</label>
            <input type="text" class="form-control" name="rate" id="rate" required> <br>

            <label for="count" class="form-label">Count</label>
            <input type="text" class="form-control" name="count" id="count" required> <br>

            <button type="button" class="btn btn-outline-info" onclick="registrarProducto()">Registrar</button>
        </form>`;

    document.getElementById("contentModal").innerHTML = cadena;
    var myModal = new bootstrap.Modal(document.getElementById('modalUsuario'));
    myModal.toggle();

    var contenedor = document.querySelector('.modal-backdrop.fade.show');


    if (contenedor) {
        contenedor.remove();
    }
}

async function registrarProducto() {
    var myForm = document.getElementById("myForm");
    var formData = new FormData(myForm);
    var jsonData = {};
    for (var [k, v] of formData) {
        jsonData[k] = v;
    }


    var xhr = new XMLHttpRequest();
    xhr.open('GET', urlApi + "/products", true);
    xhr.setRequestHeader('Accept', 'application/json');
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', localStorage.token);

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 400) {
            var response = JSON.parse(xhr.responseText);
            listar();
            alertas("Se ha registrado el producto exitosamente!", 1);
            document.getElementById("contentModal").innerHTML = '';
            var myModalEl = document.getElementById('modalProducto');
            var modal = bootstrap.Modal.getInstance(myModalEl);
            modal.hide();
            console.log(response);
        } else {

            console.error('Error al obtener la lista de productos');
        }
    };

    xhr.send(JSON.stringify(jsonData));

}