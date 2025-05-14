package com.peknight.security.pkix

import cats.effect.IO
import cats.effect.testing.scalatest.AsyncIOSpec
import com.peknight.security.pkix.pipe.{certificate, pemObject, x509Certificate}
import fs2.Stream
import org.scalatest.flatspec.AsyncFlatSpec

class PEMObjectFlatSpec extends AsyncFlatSpec with AsyncIOSpec:
  "PEM Object" should "succeed" in {
    val certPEM = s"""
       |-----BEGIN CERTIFICATE-----
       |MIIDyzCCA1GgAwIBAgISBfbG3nAyif83yCzPU2aDSXv7MAoGCCqGSM49BAMDMDIx
       |CzAJBgNVBAYTAlVTMRYwFAYDVQQKEw1MZXQncyBFbmNyeXB0MQswCQYDVQQDEwJF
       |NTAeFw0yNTA0MjIwNTU1MTVaFw0yNTA3MjEwNTU1MTRaMBkxFzAVBgNVBAMMDiou
       |cGVrbmlnaHQuY29tMHYwEAYHKoZIzj0CAQYFK4EEACIDYgAEXidKtZYHVgXgpKMf
       |D6g/YFk8vMa1Pk0dhyhLvDXCUM4HA/b5qYWdK/MZwsfV8nZPXhBQgm+EhpOqMHiC
       |tJUfVU7/gxocRoDsWz0AKG2vmAmDWyQjUyMgpMbIgnwUWApgo4ICQTCCAj0wDgYD
       |VR0PAQH/BAQDAgeAMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjAMBgNV
       |HRMBAf8EAjAAMB0GA1UdDgQWBBQ4TuyB5PyY8yBlF/dUBPGC1QBuizAfBgNVHSME
       |GDAWgBSfK1/PPCFPnQS37SssxMZwi9LXDTBVBggrBgEFBQcBAQRJMEcwIQYIKwYB
       |BQUHMAGGFWh0dHA6Ly9lNS5vLmxlbmNyLm9yZzAiBggrBgEFBQcwAoYWaHR0cDov
       |L2U1LmkubGVuY3Iub3JnLzAZBgNVHREEEjAQgg4qLnBla25pZ2h0LmNvbTATBgNV
       |HSAEDDAKMAgGBmeBDAECATAuBgNVHR8EJzAlMCOgIaAfhh1odHRwOi8vZTUuYy5s
       |ZW5jci5vcmcvMTI0LmNybDCCAQUGCisGAQQB1nkCBAIEgfYEgfMA8QB3AMz7D2qF
       |cQll/pWbU87psnwi6YVcDZeNtql+VMD+TA2wAAABllxGMGYAAAQDAEgwRgIhAKh1
       |NdMAq0FtoZyEjKGBfZ4lUujyH0mc9rpBuITNSoojAiEA7KjwHsGT3qEdTOP+ows/
       |1EoEip+WiZsgOwP+9c/diCUAdgB9WR4S4XgqexxhZ3xe/fjQh1wUoE6VnrkDL9kO
       |jC55uAAAAZZcRjBGAAAEAwBHMEUCIE41fFKzsMirSmheEI/2C0+rRwdaPGXCu2Xi
       |fXOb1JB1AiEAiaRwxecc8YkeUMSjCk+xdggjELo5aIC7Nb2FlHj0gwIwCgYIKoZI
       |zj0EAwMDaAAwZQIxAKejLF/usQKvVUWCkgWdfCQGLwSlTtTLqyLYNbSuIfRDY7gB
       |s+lQ3b9qm4cD6Wt9zgIwUP9b3WBhAk0tA2w/fLl3+an+KcvA2Iy7uqvz2I/55qO2
       |7l6YgyFqJhzKu3nfcseX
       |-----END CERTIFICATE-----
       |-----BEGIN CERTIFICATE-----
       |MIIEVzCCAj+gAwIBAgIRAIOPbGPOsTmMYgZigxXJ/d4wDQYJKoZIhvcNAQELBQAw
       |TzELMAkGA1UEBhMCVVMxKTAnBgNVBAoTIEludGVybmV0IFNlY3VyaXR5IFJlc2Vh
       |cmNoIEdyb3VwMRUwEwYDVQQDEwxJU1JHIFJvb3QgWDEwHhcNMjQwMzEzMDAwMDAw
       |WhcNMjcwMzEyMjM1OTU5WjAyMQswCQYDVQQGEwJVUzEWMBQGA1UEChMNTGV0J3Mg
       |RW5jcnlwdDELMAkGA1UEAxMCRTUwdjAQBgcqhkjOPQIBBgUrgQQAIgNiAAQNCzqK
       |a2GOtu/cX1jnxkJFVKtj9mZhSAouWXW0gQI3ULc/FnncmOyhKJdyIBwsz9V8UiBO
       |VHhbhBRrwJCuhezAUUE8Wod/Bk3U/mDR+mwt4X2VEIiiCFQPmRpM5uoKrNijgfgw
       |gfUwDgYDVR0PAQH/BAQDAgGGMB0GA1UdJQQWMBQGCCsGAQUFBwMCBggrBgEFBQcD
       |ATASBgNVHRMBAf8ECDAGAQH/AgEAMB0GA1UdDgQWBBSfK1/PPCFPnQS37SssxMZw
       |i9LXDTAfBgNVHSMEGDAWgBR5tFnme7bl5AFzgAiIyBpY9umbbjAyBggrBgEFBQcB
       |AQQmMCQwIgYIKwYBBQUHMAKGFmh0dHA6Ly94MS5pLmxlbmNyLm9yZy8wEwYDVR0g
       |BAwwCjAIBgZngQwBAgEwJwYDVR0fBCAwHjAcoBqgGIYWaHR0cDovL3gxLmMubGVu
       |Y3Iub3JnLzANBgkqhkiG9w0BAQsFAAOCAgEAH3KdNEVCQdqk0LKyuNImTKdRJY1C
       |2uw2SJajuhqkyGPY8C+zzsufZ+mgnhnq1A2KVQOSykOEnUbx1cy637rBAihx97r+
       |bcwbZM6sTDIaEriR/PLk6LKs9Be0uoVxgOKDcpG9svD33J+G9Lcfv1K9luDmSTgG
       |6XNFIN5vfI5gs/lMPyojEMdIzK9blcl2/1vKxO8WGCcjvsQ1nJ/Pwt8LQZBfOFyV
       |XP8ubAp/au3dc4EKWG9MO5zcx1qT9+NXRGdVWxGvmBFRAajciMfXME1ZuGmk3/GO
       |koAM7ZkjZmleyokP1LGzmfJcUd9s7eeu1/9/eg5XlXd/55GtYjAM+C4DG5i7eaNq
       |cm2F+yxYIPt6cbbtYVNJCGfHWqHEQ4FYStUyFnv8sjyqU8ypgZaNJ9aVcWSICLOI
       |E1/Qv/7oKsnZCWJ926wU6RqG1OYPGOi1zuABhLw61cuPVDT28nQS/e6z95cJXq0e
       |K1BcaJ6fJZsmbjRgD5p3mvEf5vdQM7MCEvU0tHbsx2I5mHHJoABHb8KVBgWp/lcX
       |GWiWaeOyB7RP+OfDtvi2OsapxXiV7vNVs7fMlrRjY1joKaqmmycnBvAq14AEbtyL
       |sVfOS66B8apkeFX2NY4XPEYV4ZSCe8VHPrdrERk2wILG3T/EGmSIkCYVUMSnjmJd
       |VQD9F6Na/+zmXCc=
       |-----END CERTIFICATE-----
    """.stripMargin
    Stream.emit(certPEM).covary[IO]
      .through(pemObject.decode[IO]).through(x509Certificate.decode[IO]())
      .through(certificate.encode[IO]).through(pemObject.encode[IO])
      .compile.toList.map(_.mkString("\n")).asserting(pem => assert(pem.trim == certPEM.trim))
  }
end PEMObjectFlatSpec
