import axios from 'axios'; 

export function onSignUp(email, password) {
    console.log(`credential received ${email} ${password}`);
    return (dispatch) => {
        return fetch(`http://localhost:8080/api/v1/sign-up`, {
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
    console.log(`credential received ${email} ${password}`);
    return (dispatch) => {
        return fetch(`http://localhost:8080/api/v1/sign-in`, {
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

export function getCurrentUser() {
    console.log(`getting current user`);
    return (dispatch) => {

        axios.get(`http://localhost:8080/api/v1/current-user`, {
            // headers
        },{
            // axios configs
            withCredentials: true
        }).then(res => {
            console.log(res.data)
        })

        // return fetch(`http://localhost:8080/api/v1/current-user`, {
        //     method: "GET",
        //     headers: {
        //         'Accept': 'application/json',
        //         'Content-Type': 'application/json',
        //         'Cache': 'no-cache'
        //     },
        //     credentials: 'include',
        // }).then(response => {
        //     if (response.ok) {
        //         console.log("ok?");
        //     }
        // })
    }
}
