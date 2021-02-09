import { GET_POSTS, CREATE_POST } from "../constants/action-types";


export function getPosts() {
    return async (dispatch) => {
        const response = await fetch("http://localhost:8080/blog/all", {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
        },
        credentials: "include",
        mode: "cors"
    })
    
    if (response.ok) {
        response.json().then(json => {
            console.log(json);
            dispatch({
                type: GET_POSTS,
                payload: json
            });
        })
    }
}
} 

export function createPost(title, body) {
    return async (dispatch) => {
        const response = await fetch("http://localhost:8080/blog/new", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            credentials: "include",
            mode: "cors",
            body: JSON.stringify({
                blogPosts: [{title: title, body: body}]
            })
        })

        if (response.ok) {
            response.json().then(json => {
                dispatch({
                    type: CREATE_POST,
                    payload: json
                })
                dispatch(getPosts())
            })
        }
    }
}