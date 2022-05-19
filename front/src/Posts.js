import { Component } from "react";
import { Container, Button, ToggleButton, Row } from "react-bootstrap";
import Post from "./Post";

class Posts extends Component {
    render() {
        return (
            <Container className="col-md-10 my-4" fluid>
                <Row>
                    <h2>게시글</h2>
                    <Container fluid>
                        <Post title="[속보]2022-1학기 경제사정 곤란 학부 재학생(코로나19 피해 학생) 장학금 신청 안내" 
                            board="컴퓨터소프트웨어학부-공지사항" 
                            keywords={[{name: "경제", color: "#dc3545"}, {name: "장학금", color: "#0d6efd"}]}
                        ></Post>
                        <Post title="[속보]2022-1학기 경제사정 곤란 학부 재학생(코로나19 피해 학생) 장학금 신청 안내" 
                            board="컴퓨터소프트웨어학부-공지사항" 
                            keywords={[{name: "경제", color: "#dc3545"}, {name: "장학금", color: "#0d6efd"}]}
                        ></Post>
                        <Post title="[속보]2022-1학기 경제사정 곤란 학부 재학생(코로나19 피해 학생) 장학금 신청 안내" 
                            board="컴퓨터소프트웨어학부-공지사항" 
                            keywords={[{name: "경제", color: "#dc3545"}, {name: "장학금", color: "#0d6efd"}]}
                        ></Post>
                        <Post title="[속보]2022-1학기 경제사정 곤란 학부 재학생(코로나19 피해 학생) 장학금 신청 안내" 
                            board="컴퓨터소프트웨어학부-공지사항" 
                            keywords={[{name: "경제", color: "#dc3545"}, {name: "장학금", color: "#0d6efd"}]}
                        ></Post>
                        <Post title="[속보]2022-1학기 경제사정 곤란 학부 재학생(코로나19 피해 학생) 장학금 신청 안내" 
                            board="컴퓨터소프트웨어학부-공지사항" 
                            keywords={[{name: "경제", color: "#dc3545"}, {name: "장학금", color: "#0d6efd"}]}
                        ></Post>
                        <Post title="[속보]2022-1학기 경제사정 곤란 학부 재학생(코로나19 피해 학생) 장학금 신청 안내" 
                            board="컴퓨터소프트웨어학부-공지사항" 
                            keywords={[{name: "경제", color: "#dc3545"}, {name: "장학금", color: "#0d6efd"}]}
                        ></Post>
                    </Container>
                </Row>
            </Container>
        );
    }
}

export default Posts;