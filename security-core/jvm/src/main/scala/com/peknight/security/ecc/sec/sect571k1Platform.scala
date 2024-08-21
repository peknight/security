package com.peknight.security.ecc.sec

import com.peknight.security.spec.ECParameterSpecPlatform

import java.math.BigInteger
import java.security.spec.{ECFieldF2m, ECParameterSpec, ECPoint, EllipticCurve}

trait sect571k1Platform extends ECParameterSpecPlatform:
  def ecParameterSpec: ECParameterSpec =
    new ECParameterSpec(
      new EllipticCurve(
        new ECFieldF2m(571, new BigInteger("7729075046034516689390703781863974688597854659412869997314470502903038284579120849072387533163845155924927232063004354354730157322085975311485817346934161497393961629647909")),
        new BigInteger("0"),
        new BigInteger("1")
      ),
      new ECPoint(
        new BigInteger("2350112116304015523482377231684766626496228526415123964355167346023168453994712131915750801804327368697642410358740900984083649787136749901162917044657353481320802543241586"),
        new BigInteger("3177153047892284027955092820645594290840691892110463136294318330308675645046690871624329737215785097835850661950174873195667225591921644089615520604151251098277510177212323")
      ),
      new BigInteger("1932268761508629172347675945465993672149463664853217499328617625725759571144780212268133978522706711834706712800825351461273674974066617311929682421617092503555733685276673"),
      4
    )
end sect571k1Platform
