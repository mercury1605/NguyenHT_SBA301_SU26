import { Card } from 'react-bootstrap';

function MyProfile({ person }) {
  return (
    <Card className="h-100 shadow-sm">
      <Card.Img variant="top" src={person.avatar} alt={person.name} style={{ objectFit: 'cover', height: '250px' }} />
      <Card.Body>
        <Card.Title>{person.name}</Card.Title>
        <Card.Text>
          {person.id}
        </Card.Text>
      </Card.Body>
    </Card>
  )
}

export default MyProfile