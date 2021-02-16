/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function myFunction() {
    setTimeout(showPage, 300);
}

function showPage() {
    document.getElementById("loaderDiv").style.display = "none";
    document.getElementById("loaderText").style.display = "none";
}

function hidePage() {
    document.getElementById("loaderDiv").style.display = "block";
    document.getElementById("loaderText").style.display = "block";
}