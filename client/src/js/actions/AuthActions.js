import axios from 'axios'; 

export function onSignUp(email, password) {
    return (dispatch) => {
        return fetch(`http://localhost:8080/sign-up`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email: email, password: password}),
        }).then(response => {
            if (response.ok) {
                console.log("ok?");
            }
        })
    }
}

export function onLogIn(email, password) {
    return (dispatch) => {
        return fetch(`http://localhost:8080/sign-in`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({email: email, password: password}),
            credentials: 'include',
        }).then(response => {
            if (response.ok) {
                console.log("Success");
            }
        })
    }
}

export function getCurrentUser() {
    console.log(`getting current user`);
    return (dispatch) => {
        return fetch(`http://localhost:8080/currentUser`, {
            method: "GET",
            headers: {
            },
            credentials: 'include',
        }).then(response => {
            if (response.ok) {
                console.log("ok?");
            }
        })
    }
}
