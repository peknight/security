package com.peknight.security.key.secret

import com.peknight.security.algorithm.Algorithm
import com.peknight.security.spec.SecretKeySpecAlgorithm

trait SecretKeyFactoryAlgorithm extends Algorithm with SecretKeyFactoryAlgorithmPlatform with SecretKeySpecAlgorithm
