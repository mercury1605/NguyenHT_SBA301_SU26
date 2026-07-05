import { Badge, Button, Image, Table } from 'react-bootstrap';

function OrchidTable({ orchids, onEdit, onDelete }) {
  return (
    <Table striped bordered hover responsive>
      <thead className="table-dark">
        <tr>
          <th>#</th>
          <th>Image</th>
          <th>Name</th>
          <th>Description</th>
          <th>Category</th>
          <th>Natural</th>
          <th>Attractive</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        {orchids.length === 0 ? (
          <tr>
            <td colSpan={8} className="text-center text-muted">
              No orchids found
            </td>
          </tr>
        ) : (
          orchids.map((orchid, index) => (
            <tr key={orchid.orchidId}>
              <td>{index + 1}</td>
              <td>
                <Image
                  src={orchid.orchidURL}
                  alt={orchid.orchidName}
                  width={80}
                  height={60}
                  style={{ objectFit: 'cover' }}
                  rounded
                  onError={(e) => {
                    e.target.style.display = 'none';
                  }}
                />
              </td>
              <td>{orchid.orchidName}</td>
              <td>{orchid.orchidDescription}</td>
              <td>{orchid.orchidCategory}</td>
              <td>
                {orchid.isNatural ? (
                  <Badge bg="success">Natural</Badge>
                ) : (
                  <Badge bg="secondary">Artificial</Badge>
                )}
              </td>
              <td>
                {orchid.isAttractive ? (
                  <Badge bg="warning" text="dark">
                    Attractive
                  </Badge>
                ) : (
                  <Badge bg="light" text="dark">
                    Normal
                  </Badge>
                )}
              </td>
              <td className="d-flex gap-2">
                <Button
                  variant="outline-primary"
                  size="sm"
                  onClick={() => onEdit(orchid.orchidId)}
                >
                  Edit
                </Button>
                <Button
                  variant="outline-danger"
                  size="sm"
                  onClick={() => onDelete(orchid.orchidId)}
                >
                  Delete
                </Button>
              </td>
            </tr>
          ))
        )}
      </tbody>
    </Table>
  );
}

export default OrchidTable;
