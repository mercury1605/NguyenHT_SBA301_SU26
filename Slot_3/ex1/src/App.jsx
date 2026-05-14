import './App.css'
import MyProfile from './components/MyProfile'
import 'bootstrap/dist/css/bootstrap.min.css';
import { Container, Row, Col } from 'react-bootstrap';

function App() {

  const students = [
    { name: 'Quang', id: 'DE190511', avatar: '/images/fpt_ava.jpg' },
    { name: 'Ha', id: 'DE190872', avatar: '/images/fpt_ava.jpg' },
    { name: 'Tuan', id: 'DE191034', avatar: '/images/fpt_ava.jpg' },
    { name: 'Ngoc', id: 'DE190699', avatar: '/images/fpt_ava.jpg' },
    { name: 'Vy', id: 'DE190284', avatar: '/images/fpt_ava.jpg' },
    { name: 'Son', id: 'DE191220', avatar: '/images/fpt_ava.jpg' },
    { name: 'Mai', id: 'DE190947', avatar: '/images/fpt_ava.jpg' },
    { name: 'Bao', id: 'DE191105', avatar: '/images/fpt_ava.jpg' },
    { name: 'Thao', id: 'DE190638', avatar: '/images/fpt_ava.jpg' },
    { name: 'Viet', id: 'DE191387', avatar: '/images/fpt_ava.jpg' }
  ];

  return (
    <Container className="py-4">
      <h2 className="text-center mb-4">List of Profiles</h2>
      <Row xs={1} sm={2} md={3} className="g-4">
        {students.map((student, index) => (
          <Col key={index}>
            <MyProfile person={student} />
          </Col>
        ))}
      </Row>
    </Container>
  )

}

export default App