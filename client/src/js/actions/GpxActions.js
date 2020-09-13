import { UPLOAD_GPX } from "../constants/action-types";

export function uploadGpxFile(file) {
    const formData = new FormData()
    formData.append('myFile', file)
    return (dispatch) => {
        return fetch("http://localhost:8080/route/import/gpx", {
            method: "POST",
            body: formData,
            credentials: "include",
        }).then(response => {
            response
        });
    }
}