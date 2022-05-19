import React, { useState } from 'react';

import { Container, Row } from 'react-bootstrap';
import './App.css';
import Header from './Header';
import Keywords from './Keywords';
import Posts from './Posts';

function App() {
  return (
    <Container className='bg-light m-0 p-0' fluid>
      <Header></Header>
      <Container className="bg-light">
        <Row>
          <Keywords></Keywords>
        </Row>
        <Row>
          <Posts></Posts>
        </Row>
      </Container>
    </Container>
  );
}

export default App;
