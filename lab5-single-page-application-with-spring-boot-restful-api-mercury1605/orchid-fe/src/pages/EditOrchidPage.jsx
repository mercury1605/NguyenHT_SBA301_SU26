import { useEffect, useState } from 'react';
import { Alert, Breadcrumb, Spinner } from 'react-bootstrap';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { useOrchid } from '../context/OrchidContext';
import OrchidForm from '../components/OrchidForm';

export default function EditOrchidPage() {
  const { id } = useParams();
  const { fetchOrchidById, editOrchid } = useOrchid();
  const navigate = useNavigate();

  const [orchid, setOrchid] = useState(null);
  const [fetching, setFetching] = useState(true);
  const [fetchError, setFetchError] = useState('');
  const [saving, setSaving] = useState(false);
  const [success, setSuccess] = useState(false);
  const [errorMsg, setErrorMsg] = useState('');

  useEffect(() => {
    fetchOrchidById(id)
      .then((data) => setOrchid(data))
      .catch(() => setFetchError(`Orchid with ID #${id} not found.`))
      .finally(() => setFetching(false));
  }, [id, fetchOrchidById]);

  const handleSubmit = async (data) => {
    setSaving(true);
    setErrorMsg('');
    try {
      await editOrchid(id, data);
      setSuccess(true);
      setTimeout(() => navigate('/'), 1500);
    } catch (err) {
      setErrorMsg(err.message || 'An error occurred while updating the orchid.');
    } finally {
      setSaving(false);
    }
  };

  return (
    <div style={{ maxWidth: '720px' }} className="mx-auto">
      <Breadcrumb>
        <Breadcrumb.Item linkAs={Link} linkProps={{ to: '/' }}>
          Home
        </Breadcrumb.Item>
        <Breadcrumb.Item active>Edit Orchid #{id}</Breadcrumb.Item>
      </Breadcrumb>

      <h2 className="mb-4">Edit Orchid #{id}</h2>

      {fetching && (
        <div className="text-center py-5">
          <Spinner animation="border" role="status">
            <span className="visually-hidden">Loading...</span>
          </Spinner>
        </div>
      )}

      {fetchError && <Alert variant="danger">{fetchError}</Alert>}

      {success && (
        <Alert variant="success">
          Orchid updated successfully! Redirecting to home...
        </Alert>
      )}

      {errorMsg && <Alert variant="danger">{errorMsg}</Alert>}

      {!fetching && orchid !== null && (
        <OrchidForm
          initialData={orchid}
          onSubmit={handleSubmit}
          submitLabel="Update Orchid"
          loading={saving}
        />
      )}
    </div>
  );
}
