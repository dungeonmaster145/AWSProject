import React,{useState,useEffect,useCallback}  from 'react';

import './App.css';
import axios from "axios";
import {useDropzone} from 'react-dropzone';
//the user profile is the functional component that we are going to create and we are going to pass that component in the div function

const UserProfile =()=>{
  const[userProfile,setUserProfile] =useState([]);
  const fetchUserProfiles=()=>{
    axios.get("http://localhost:8080/api/v1/user-profile").then(res=>{
      console.log(res);
      setUserProfile(res.data);

    });
  };

useEffect(()=>{
  fetchUserProfiles();
},[]);
//use effect is used because if anything changes inside this list of the two users that we have created then this will get triggered the useEffect function
return userProfile.map((userProfile,index)=>{
  return(
    <div key="index">
      {userProfile.userProfileId ? <img src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileId}/image/download`} />:null }
     <br/>
      <h1>{userProfile.username}</h1>
      <p>{userProfile.userProfileId}</p>
      <Dropzone {...userProfile}/>
    
      <br />  
    </div>
  );
    
  
});

};
//the above userProfile is added to link it to the parameter that we had passed in the dropzone function
function Dropzone({userProfileId}) {
  const onDrop = useCallback(acceptedFiles => {
    // Do something with the files
    const file=acceptedFiles[0];
    const formData=new FormData(); 
    formData.append("file",file); //here we are appending the file that we are goint ot upload on the server and store it in the database
    axios.post(`http://localhost:8080/api/v1/user-profile/${userProfileId}/image/upload`,
    formData,
    {
      headers:
      {
        "Content-Type": "multipart/form-data"
      }
    }).then(()=>
    {
      console.log("file uploaded successfully")
  }
  ).catch(err=>{
      console.log(err);
    });
    console.log(file);
    
  }, []);
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image or click to select profile image</p>
      }
    </div>
  )
}


function App() {
  
  return (
    <div className="App">
     <UserProfile />
    </div>
  );
}

export default App;
