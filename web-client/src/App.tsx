import React from 'react';
import './App.css';
import {LoginPage} from "./pages/LoginPage";
import {AnimateInputControl} from "./Components/AnimateInputControl";

function App() {
  return (
    <div className="App">
      <LoginPage />

        <div>
            <AnimateInputControl labelName={"First Name"} />
        </div>

    </div>
  );
}

export default App;
