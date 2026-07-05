import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { Container, Alert, Breadcrumb } from 'react-bootstrap'
import { useOrchid } from '../context/OrchidContext'
import OrchidForm from '../components/OrchidForm'

export default function AddOrchidPage() {
  const navigate = useNavigate()
  const { addOrchid } = useOrchid()
  const [saving, setSaving] = useState(false)
  const [success, setSuccess] = useState(false)
  const [error, setError] = useState('')

  const handleSubmit = async (data) => {
    setSaving(true); setError('')
    try { await addOrchid(data); setSuccess(true); setTimeout(() => navigate('/'), 1500) }
    catch (err) { setError(err.response?.data?.message ?? 'Failed to add.') }
    finally { setSaving(false) }
  }

  return (
    <Container className="py-4" style={{ maxWidth:720 }}>
      <Breadcrumb>
        <Breadcrumb.Item href="/">Home</Breadcrumb.Item>
        <Breadcrumb.Item active>Add orchid</Breadcrumb.Item>
      </Breadcrumb>
      <h3 className="mb-4">Add new orchid</h3>
      {success && <Alert variant="success">Added successfully! Redirecting...</Alert>}
      {error && <Alert variant="danger">{error}</Alert>}
      <OrchidForm onSubmit={handleSubmit} submitLabel="Add orchid" loading={saving} />
    </Container>
  )
}
