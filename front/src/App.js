import React, { Component, useState } from 'react';

import { Container, Row } from 'react-bootstrap';
import './App.css';
import Header from './Header';
import Keywords from './Keywords';
import Posts from './Posts';

class App extends Component {

  constructor(props) {
    super(props);
    
    document.title = "HY-notice";
    let keywords = [];
    if (localStorage.getItem('keywords')) {
      keywords = JSON.parse(localStorage.getItem('keywords'));
    }
    let readIds = new Set();
    if (localStorage.getItem('readIds')) {
      readIds = new Set(JSON.parse(localStorage.getItem('readIds')));
    }
    this.state = {keywords: keywords, readIds: readIds};
    this.regex = /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9|]+$/;
  }

  strToColor(str) {
    var hash = 0, i, chr;
    if (str.length === 0) return hash;
    for (i = 0; i < str.length; i++) {
      chr   = str.charCodeAt(i);
      hash  = ((hash << 5) - hash) + chr;
      hash |= 0;
    }
    var color = '#';
    for (var i = 0; i < 3; i++) {
      var value = (hash >> (i * 8)) & 0xFF;
      value %= 196;
      color += ('00' + value.toString(16)).substr(-2);
    }
    return color;
  }

  deleteKeyword(id) {
    this.setState(state => {
        return {
            keywords: state.keywords.slice(0, id).concat(state.keywords.slice(id + 1))
        }
    })
  }

  componentDidUpdate() {
    localStorage.setItem('keywords', JSON.stringify(this.state.keywords))
    localStorage.setItem('readIds', JSON.stringify(Array.from(this.state.readIds)))
  }

  readId(id) {
    const newReadIds = new Set([id]);
    Array.from(this.state.readIds).forEach(readId => {
      newReadIds.add(readId);
    });
    this.setState(state => {return {readIds: newReadIds}})
  }

  addKeyword(newKeyword) {
    this.setState(state => {return {keywords: state.keywords.concat({name: newKeyword, color:this.strToColor(newKeyword)})}});
    this.setState({keywordModalShow: false});
  }

  isValidKeyword(newKeyword) {
    if (newKeyword.length < 2 || newKeyword.length > 20) {
      return false;
    }

    if (!this.regex.test(newKeyword)) {
      return false;
    }

    return !this.state.keywords.includes(newKeyword);
  }

  render() {
    return (
      <Container className='bg-light m-0 p-0' fluid>
        <Header></Header>
        <Container className="bg-light">
          <Row>
            <Keywords keywords={this.state.keywords} isValidKeyword={this.isValidKeyword.bind(this)} deleteKeyword={this.deleteKeyword.bind(this)} componentDidUpdate={this.componentDidUpdate.bind(this)} addKeyword={this.addKeyword.bind(this)}></Keywords>
          </Row>
          <Row>
            <Posts keywords={this.state.keywords} readIds={this.state.readIds} readId={this.readId.bind(this)}></Posts>
          </Row>
        </Container>
      </Container>
    );
  }
}

export default App;
