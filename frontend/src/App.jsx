import { useEffect, useMemo, useState } from 'react'
import { Search, MapPin, Star, Wifi, Waves, Dumbbell, Utensils, CalendarDays, ArrowRight, X, Menu, ShieldCheck, Plane, ChevronDown, BedDouble, CheckCircle2, Ticket, GitCompareArrows } from 'lucide-react'

const money = n => new Intl.NumberFormat('en-IN', { style: 'currency', currency: 'INR', maximumFractionDigits: 0 }).format(n)
const today = new Date().toISOString().slice(0, 10)
const tomorrow = new Date(Date.now() + 86400000).toISOString().slice(0, 10)

function App() {
  const [hotels, setHotels] = useState([])
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState('')
  const [location, setLocation] = useState('')
  const [checkIn, setCheckIn] = useState(today)
  const [checkOut, setCheckOut] = useState(tomorrow)
  const [selected, setSelected] = useState(null)
  const [bookingRoom, setBookingRoom] = useState(null)
  const [guestName, setGuestName] = useState('')
  const [email, setEmail] = useState('')
  const [notice, setNotice] = useState('')
  const [booking, setBooking] = useState(null)
  const [lookupRef, setLookupRef] = useState('')
  const [lookupEmail, setLookupEmail] = useState('')
  const [lookupResult, setLookupResult] = useState(null)
  const [compare, setCompare] = useState([])
  const [mobileMenu, setMobileMenu] = useState(false)

  const nights = Math.max(1, Math.round((new Date(checkOut) - new Date(checkIn)) / 86400000))
  const loadHotels = async () => {
    setLoading(true); setError('')
    try {
      const response = await fetch(`/api/hotels${location.trim() ? `?location=${encodeURIComponent(location.trim())}` : ''}`)
      if (!response.ok) throw new Error('Could not load hotels. Is the backend running?')
      setHotels(await response.json())
    } catch (e) { setError(e.message) } finally { setLoading(false) }
  }
  useEffect(() => { loadHotels() }, [])

  const amenitiesIcons = [Wifi, Waves, Dumbbell, Utensils]
  const toggleCompare = hotel => setCompare(current => current.some(h => h.id === hotel.id)
    ? current.filter(h => h.id !== hotel.id)
    : current.length < 3 ? [...current, hotel] : current)

  async function submitBooking(e) {
    e.preventDefault(); setNotice(''); setBooking(null)
    if (!bookingRoom || !selected) return
    try {
      const response = await fetch('/api/bookings', {
        method: 'POST', headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ hotelId: selected.id, roomId: bookingRoom.id, guestName, email, checkIn, checkOut })
      })
      const data = await response.json()
      if (!response.ok) throw new Error(data.message || 'Booking failed.')
      setBooking(data); setNotice('Your booking is confirmed.')
      setBookingRoom(null); setGuestName(''); setEmail('')
    } catch (e) { setNotice(e.message) }
  }

  async function retrieveBooking(e) {
    e.preventDefault(); setLookupResult(null); setNotice('')
    try {
      const response = await fetch(`/api/bookings/${encodeURIComponent(lookupRef.trim())}?email=${encodeURIComponent(lookupEmail.trim())}`)
      const data = await response.json()
      if (!response.ok) throw new Error(data.message || 'Booking not found.')
      setLookupResult(data)
    } catch (e) { setNotice(e.message) }
  }

  async function cancelBooking() {
    if (!lookupResult || !confirm('Cancel this booking?')) return
    try {
      const response = await fetch(`/api/bookings/${encodeURIComponent(lookupResult.bookingReference)}?email=${encodeURIComponent(lookupEmail.trim())}`, { method: 'DELETE' })
      const data = await response.json()
      if (!response.ok) throw new Error(data.message || 'Cancellation failed.')
      setLookupResult(data); setNotice('Booking cancelled successfully.')
    } catch (e) { setNotice(e.message) }
  }

  return <div className="app">
    <div className="top-strip"><div className="container strip-inner"><span><Plane size={14}/> Your journey, our passion</span><span>24×7 travel assistance <b>1800 2099 100</b></span></div></div>
    <header className="header">
      <div className="container nav">
        <a className="brand" href="#"><span className="brand-mark">TC</span><span>thomas<span className="brand-light">cook</span><small>INDIA</small></span></a>
        <button className="menu-toggle" onClick={() => setMobileMenu(!mobileMenu)}><Menu/></button>
        <nav className={mobileMenu ? 'nav-links open' : 'nav-links'}>
          <a href="#stays">Hotels & Stays</a><a href="#manage">Manage Booking</a><a href="#why">Why book with us</a>
        </nav>
        <a className="help-link" href="#manage">Need help? <b>Contact us</b></a>
      </div>
    </header>

    <main>
      <section className="hero">
        <div className="hero-image"></div>
        <div className="container hero-content">
          <div className="eyebrow"><span></span> FIND YOUR PERFECT STAY</div>
          <h1>Make room for<br/><em>new memories.</em></h1>
          <p>Discover stays that make every journey special.</p>
          <div className="search-panel">
            <label><span><MapPin size={16}/> DESTINATION</span><input value={location} onChange={e => setLocation(e.target.value)} placeholder="Where do you want to go?"/></label>
            <label><span><CalendarDays size={16}/> CHECK IN</span><input type="date" min={today} value={checkIn} onChange={e => {setCheckIn(e.target.value); if (checkOut <= e.target.value) setCheckOut(new Date(new Date(e.target.value).getTime()+86400000).toISOString().slice(0,10))}}/></label>
            <label><span><CalendarDays size={16}/> CHECK OUT</span><input type="date" min={checkIn || tomorrow} value={checkOut} onChange={e => setCheckOut(e.target.value)}/></label>
            <button className="search-btn" onClick={loadHotels}><Search size={18}/> Search stays</button>
          </div>
        </div>
        <div className="hero-caption">THOUGHTFUL STAYS. MEMORABLE JOURNEYS.</div>
      </section>

      <section className="container trust-row" id="why">
        <div><ShieldCheck/><span><b>Trusted stays</b><small>Curated hotel choices</small></span></div>
        <div><Ticket/><span><b>Easy booking</b><small>Simple, secure process</small></span></div>
        <div><Plane/><span><b>Travel with ease</b><small>Support when you need it</small></span></div>
      </section>

      <section className="container section" id="stays">
        <div className="section-heading"><div><div className="eyebrow dark">HANDPICKED FOR YOU</div><h2>Stay somewhere <em>special.</em></h2></div><span className="result-count">{hotels.length} stays to explore</span></div>
        {error && <div className="alert error">{error} <button onClick={loadHotels}>Retry</button></div>}
        {loading ? <div className="loading">Finding lovely places to stay…</div> :
          <div className="hotel-grid">{hotels.map((hotel, index) => <article className="hotel-card" key={hotel.id}>
            <div className="hotel-photo"><img src={hotel.imageUrl} alt={hotel.name} onError={e => e.currentTarget.src='https://images.unsplash.com/photo-1566073771259-6a8506099945?w=900'}/><span className="photo-tag">GUEST FAVOURITE</span><button className={compare.some(h=>h.id===hotel.id)?'compare active':'compare'} onClick={()=>toggleCompare(hotel)}><GitCompareArrows size={15}/>{compare.some(h=>h.id===hotel.id)?'Added':'Compare'}</button></div>
            <div className="hotel-info"><div className="hotel-location"><MapPin size={13}/>{hotel.location}</div><div className="hotel-title-row"><h3>{hotel.name}</h3><span className="rating"><Star size={13} fill="currentColor"/>{hotel.rating}</span></div><p>{hotel.description}</p><div className="amenities">{hotel.amenities.slice(0,4).map((a,i)=><span key={a}>{a}</span>)}</div><div className="hotel-bottom"><div><small>STARTING FROM</small><strong>{money(Math.min(...hotel.rooms.map(r=>Number(r.pricePerNight))))}</strong><small>per room / night</small></div><button className="text-btn" onClick={()=>setSelected(hotel)}>Explore stay <ArrowRight size={16}/></button></div></div>
          </article>)}</div>}
        {compare.length > 0 && <div className="compare-panel"><div><b>Compare stays</b><small>{compare.length} of 3 selected</small></div>{compare.map(h=><span key={h.id}>{h.name} <button onClick={()=>toggleCompare(h)}><X size={13}/></button></span>)}{compare.length>=2 && <div className="compare-table">{compare.map(h=><div key={h.id}><b>{h.name}</b><span>{h.location}</span><span>★ {h.rating}</span><strong>{money(Math.min(...h.rooms.map(r=>Number(r.pricePerNight))))}/night</strong></div>)}</div>}</div>}
      </section>

      <section className="manage-section" id="manage"><div className="container manage-grid"><div><div className="eyebrow">YOUR TRIP, YOUR WAY</div><h2>Plans can change.<br/><em>We make it easy.</em></h2><p>Look up your reservation or cancel a booking using your booking reference and email address.</p><div className="manage-art"><Ticket size={76}/><span>YOUR JOURNEY<br/>AT YOUR FINGERTIPS</span></div></div>
        <div className="manage-card"><h3>Manage your booking</h3><p>Enter the details from your confirmation.</p><form onSubmit={retrieveBooking}><label>BOOKING REFERENCE<input required value={lookupRef} onChange={e=>setLookupRef(e.target.value)} placeholder="e.g. TC12AB34CD56"/></label><label>EMAIL ADDRESS<input required type="email" value={lookupEmail} onChange={e=>setLookupEmail(e.target.value)} placeholder="Email used at booking"/></label><button className="primary full">Find my booking <ArrowRight size={16}/></button></form>
          {lookupResult && <div className="booking-result"><div className="result-top"><CheckCircle2/><b>{lookupResult.status}</b></div><h4>{lookupResult.hotelName}</h4><p>{lookupResult.roomType} · {lookupResult.nights} night(s)</p><p>{lookupResult.checkIn} → {lookupResult.checkOut}</p><strong>{money(lookupResult.totalAmount)}</strong><small>Reference: {lookupResult.bookingReference}</small>{lookupResult.status==='CONFIRMED' && <button className="cancel-btn" onClick={cancelBooking}>Cancel booking</button>}</div>}
          {notice && <div className="alert">{notice}</div>}
        </div></div></section>
      <footer><div className="container footer-inner"><a className="brand footer-brand" href="#"><span className="brand-mark">TC</span><span>thomas<span className="brand-light">cook</span><small>INDIA</small></span></a><span>© 2026 Thomas Cook-style assessment demo. Sample properties and prices are fictional.</span><a href="#stays">Back to stays ↑</a></div></footer>
    </main>

    {selected && <div className="modal-backdrop" onClick={()=>setSelected(null)}><div className="modal" onClick={e=>e.stopPropagation()}><button className="modal-close" onClick={()=>{setSelected(null);setBookingRoom(null)}}><X/></button><img className="modal-image" src={selected.imageUrl} alt={selected.name}/><div className="modal-body"><div className="hotel-location"><MapPin size={14}/>{selected.address}</div><h2>{selected.name}</h2><p>{selected.description}</p><h3>Rooms & rates</h3>{selected.rooms.map(room=><div className="room-row" key={room.id}><div><b>{room.roomType}</b><small>{room.description}</small></div><strong>{money(room.pricePerNight)}<small>/ night</small></strong><button onClick={()=>setBookingRoom(room)}>Book</button></div>)}{bookingRoom && <form className="booking-form" onSubmit={submitBooking}><h3>Complete your reservation</h3><div className="form-grid"><label>Full name<input required maxLength="120" value={guestName} onChange={e=>setGuestName(e.target.value)} placeholder="Your name"/></label><label>Email<input required type="email" value={email} onChange={e=>setEmail(e.target.value)} placeholder="you@example.com"/></label></div><div className="booking-summary"><span>{nights} night(s) × {money(bookingRoom.pricePerNight)}</span><b>{money(nights*Number(bookingRoom.pricePerNight))}</b></div><button className="primary full">Confirm booking</button></form>}{booking && <div className="success-box"><CheckCircle2/><h3>You're booked!</h3><p>Save your booking reference</p><strong>{booking.bookingReference}</strong><p>{booking.hotelName} · {money(booking.totalAmount)}</p><button onClick={()=>{setSelected(null);setBooking(null)}}>Done</button></div>}</div></div></div>}
  </div>
}
export default App
