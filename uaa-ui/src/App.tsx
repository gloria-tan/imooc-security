import React from 'react';
import logo from './logo.svg';
import './App.css';
import { LoginForm } from './pages/auth/LoginForm';
import { MfaForm } from './pages/auth/MfaForm';

function App() {
  return (
    <h1 className="text-3xl font-bold underline text-red-500">
      Hello world!

      <LoginForm />

      <MfaForm />
    </h1>


  );
}

export default App;
