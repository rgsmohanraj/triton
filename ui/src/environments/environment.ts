// import environmentValue from  '../tritonEnvironment.json'
// const  envVal = 'dev';
export const environment = {
  production: false,
//        baseUrl: environmentValue.envVal.baseUrl,
//       baseUrl: "https://triton.vivriticapital.com/",
//          baseUrl: "http://localhost:8080/",
       baseUrl: "http://10.100.10.41:8080/",
//       baseUrl: "http://10.100.10.230:8080/",

    firebase: {
        apiKey: "AIzaSyAmB1rnx-KDWPYleqHcTmU1I57-kZczrkA",
        authDomain: "react-endless.firebaseapp.com",
        databaseURL: "https://react-endless.firebaseio.com",
        projectId: "react-endless",
        storageBucket: "react-endless.appspot.com",
        messagingSenderId: "179755231748",
        appId: "1:179755231748:web:44d12bfe9196f209256351",
    }
};
