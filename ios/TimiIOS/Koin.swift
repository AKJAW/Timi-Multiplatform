//
//  KoinApplication.swift
//  KaMPStarteriOS
//
//  Created by Russell Wolf on 6/18/20.
//  Copyright © 2020 Touchlab. All rights reserved.
//

import Foundation
import shared

func startKoin() {
    // You could just as easily define all these dependencies in Kotlin,
    // but this helps demonstrate how you might pass platform-specific
    // dependencies in a larger scale project where declaring them in
    // Kotlin is more difficult, or where they're also used in
    // iOS-specific code.

    let koinApplication = KoinIOSKt.doInitKoinIos()
    _koin = koinApplication.koin
}

private var _koin: Koin_coreKoin?
var koin: Koin_coreKoin {
    return _koin!
}

// swiftlint:disable force_cast
struct KoinWrapper {
    static func get<T>(type: AnyClass) -> T {
        koin.get(objCClass: type) as! T
    }
    static func get<T>(typeProtocol: Protocol) -> T {
        koin.get(objCProtocol: typeProtocol) as! T
    }
}
