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
    let includedKeywords = [];
    if (localStorage.getItem('includedKeywords')) {
      includedKeywords = JSON.parse(localStorage.getItem('includedKeywords'));
    }
    let excludedKeywords = [];
    if (localStorage.getItem('excludedKeywords')) {
      excludedKeywords = JSON.parse(localStorage.getItem('excludedKeywords'));
    }
    let readIds = new Set();
    if (localStorage.getItem('readIds')) {
      readIds = new Set(JSON.parse(localStorage.getItem('readIds')));
    }
    this.state = {includedKeywords: includedKeywords, excludedKeywords: excludedKeywords, readIds: readIds};
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

  deleteIncludedKeyword(id) {
    this.setState(state => {
        return {
            includedKeywords: state.includedKeywords.slice(0, id).concat(state.includedKeywords.slice(id + 1))
        }
    });
  }

  deleteExcludedKeyword(id) {
    this.setState(state => {
      return {
          excludedKeywords: state.excludedKeywords.slice(0, id).concat(state.excludedKeywords.slice(id + 1))
      }
    });
  }

  componentDidUpdate() {
    localStorage.setItem('includedKeywords', JSON.stringify(this.state.includedKeywords));
    localStorage.setItem('excludedKeywords', JSON.stringify(this.state.excludedKeywords));
    localStorage.setItem('readIds', JSON.stringify(Array.from(this.state.readIds)));
  }

  readId(id) {
    const newReadIds = new Set([id]);
    Array.from(this.state.readIds).forEach(readId => {
      newReadIds.add(readId);
    });
    this.setState(state => {return {readIds: newReadIds}})
  }

  addIncludedKeyword(newKeyword) {
    this.setState(state => {return {includedKeywords: state.includedKeywords.concat({name: newKeyword, color:this.strToColor(newKeyword)})}});
    this.setState({keywordModalShow: false});
  }

  addExcludedKeyword(newKeyword) {
    this.setState(state => {return {excludedKeywords: state.excludedKeywords.concat({name: newKeyword, color: "#000000"})}});
    this.setState({keywordModalShow: false});
  }

  isValidKeyword(newKeyword) {
    if (newKeyword.length < 2 || newKeyword.length > 20) {
      return false;
    }

    if (!this.regex.test(newKeyword)) {
      return false;
    }

    if (this.state.includedKeywords.length + this.state.excludedKeywords.length >= 40) {
      return false;
    }

    for (const i in this.state.includedKeywords) {
      const keyword = this.state.includedKeywords[i].name;
      if (keyword === newKeyword) {
        return false;
      }
    }

    for (const i in this.state.excludedKeywords) {
      const keyword = this.state.excludedKeywords[i].name;
      if (keyword === newKeyword) {
        return false;
      }
    }

    return true;
  }

  render() {
    return (
      <Container className='bg-light m-0 p-0' fluid>
        <Header></Header>
        <Container className="bg-light">
          <Row>
            <Keywords title="포함 키워드" keywords={this.state.includedKeywords} isValidKeyword={this.isValidKeyword.bind(this)} deleteKeyword={this.deleteIncludedKeyword.bind(this)} componentDidUpdate={this.componentDidUpdate.bind(this)} addKeyword={this.addIncludedKeyword.bind(this)}></Keywords>
          </Row>
          <Row>
            <Keywords title="제외 키워드" keywords={this.state.excludedKeywords} isValidKeyword={this.isValidKeyword.bind(this)} deleteKeyword={this.deleteExcludedKeyword.bind(this)} componentDidUpdate={this.componentDidUpdate.bind(this)} addKeyword={this.addExcludedKeyword.bind(this)}></Keywords>
          </Row>
          <Row>
            <Posts includedKeywords={this.state.includedKeywords} excludedKeywords={this.state.excludedKeywords} readIds={this.state.readIds} readId={this.readId.bind(this)}></Posts>
          </Row>
        </Container>
      </Container>
    );
  }
}

export default App;
