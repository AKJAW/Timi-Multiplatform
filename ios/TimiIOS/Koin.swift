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
    static func get<T>(typeProtocol: Protocol, parameter: Any) -> T {
        koin.get(objCProtocol: typeProtocol, parameter: parameter) as! T
    }
}
